package com.example.jualbukuid.ui

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jualbukuid.R
import com.example.jualbukuid.camera.rotateBitmap
import com.example.jualbukuid.databinding.ActivityResultBinding
import com.example.jualbukuid.models.CameraViewModel
import com.example.jualbukuid.models.SharedViewModel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val myFile = sharedViewModel.getFile().value

        Log.d("FILE PINDAH", myFile.toString())

        Glide.with(this).load(myFile).into(binding.imResultPhoto)


        binding.btnProceed.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }
}