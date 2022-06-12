package com.example.jualbukuid.ui

import android.Manifest
import android.R.attr
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.jualbukuid.camera.CameraXActivity
import com.example.jualbukuid.camera.rotateBitmap
import com.example.jualbukuid.databinding.ActivityCameraBinding
import com.example.jualbukuid.models.CameraViewModel
import com.example.jualbukuid.models.SharedViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class CameraActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private lateinit var binding: ActivityCameraBinding
    private lateinit var viewModel: CameraViewModel
    private val sharedViewModel: SharedViewModel by viewModels()
    private var file: File? = null
    private lateinit var result: Bitmap
    private lateinit var trashName: String
    private lateinit var trashWeight: String


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CameraViewModel::class.java)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnTakePhoto.setOnClickListener { startCameraX() }
        binding.btnUpload.setOnClickListener {
            trashName = binding.etNamaSampah.text.toString()
            trashWeight = binding.etBeratSampah.text.toString()

            when {
                trashName.isEmpty() -> {
                    binding.etNamaSampah.error = "Harus Diisi"
                    binding.etNamaSampah.requestFocus()
                    return@setOnClickListener
                }
                trashWeight.isEmpty() -> {
                    binding.etBeratSampah.error = "Harus Diisi"
                    binding.etBeratSampah.requestFocus()
                    return@setOnClickListener
                }
                else -> uploadData()
            }
        }



    }

    private val launchCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            file = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.imResultPhoto.setImageBitmap(result)
        }
    }

    private fun startCameraX(){
        launchCameraX.launch(Intent(this, CameraXActivity::class.java))
    }

    private fun uploadData(){
        if (file != null){

            val storageReference = FirebaseStorage.getInstance().getReference("Trash")
            val trashRef = storageReference.child("trash.jpg")

            val bitmap = (binding.imResultPhoto.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = trashRef.putBytes(data)
            uploadTask.addOnSuccessListener{
                startActivity(Intent(this, ResultActivity::class.java))
            }.addOnFailureListener{
                //TODO
            }

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                trashRef.downloadUrl
            }.addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Toast.makeText(this,"$downloadUri", Toast.LENGTH_SHORT).show()

                    viewModel.sendPhoto("$downloadUri")
                    sharedViewModel.setFile("$downloadUri")

                } else {
                    // Handle failures
                    // ...
                }
            }

        }
    }
}