package com.example.jualbukuid.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jualbukuid.databinding.ActivityRegisterBinding
import com.example.jualbukuid.models.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var errormsg: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            getData()
        }

    }

    private fun getData(){
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

        binding.apply {
            name = binding.etNameRegister.text.toString()
            phone = binding.etHpRegister.text.toString()
            email = binding.etEmailRegister.text.toString()
            pass = binding.etPasswordRegister.text.toString()
        }

        viewModel.setRegis(name, phone, email, pass)

        viewModel.getRegis().observe(this) {
            if (it != null) {
                errormsg = it.error.toString()
                Log.d("Error Message", errormsg)
            }
            if (errormsg == "false"){
                showLoading(true)
                Toast.makeText(this, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
                showLoading(false)
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Gagal Daftar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Register Activity"
    }
}