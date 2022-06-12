package com.example.jualbukuid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.jualbukuid.MainActivity
import com.example.jualbukuid.api.ApiConfig
import com.example.jualbukuid.databinding.ActivityLoginBinding
import com.example.jualbukuid.models.*
import com.example.jualbukuid.response.LoginResponse
import retrofit2.Call
import retrofit2.Response


private val Context.data: DataStore<Preferences> by preferencesDataStore(name = "token")

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginResponse: LoginResponse
    private lateinit var preference: UserPreference
//    private lateinit var sharedUserPreferences: SharedUserPreferences
    private lateinit var binding: ActivityLoginBinding
    private lateinit var user: User
    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var errormsg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setup(){
        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(data), this)
        )[LoginViewModel::class.java]
        loginViewModel.getUser().observe(this){ user ->
            if (user.isLogin){
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }


    private fun login(){

        binding.apply {
            email = binding.etEmail.text.toString()
            pass = binding.etPassword.text.toString()
        }

        loginViewModel.setLogin(email, pass)
        loginViewModel.setData(email, pass)

        loginViewModel.getData().observe(this){
            if (it != null){
                name = it.name.toString()
                email = it.email.toString()
                phone = it.phone.toString()
            }
//            sharedUserPreferences.saveName(name)
//            sharedUserPreferences.saveEmail(email)
//            sharedUserPreferences.savePhone(phone)
        }

        loginViewModel.getLogin().observe(this){
            if (it != null){
                errormsg = it.status.toString()
                Log.d("Error Message", errormsg)
            }
            if (errormsg == "true"){
                Toast.makeText(this, "Anda Berhasil Login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }



//    private fun setup() {
//        loginViewModel = ViewModelProvider(
//            this, ViewModelFactory(UserPreference.getInstance(dataStore))
//        )[SharedViewModel::class.java]
//
//        loginViewModel.getUser().observe(this) { user ->
//            if (user.isLogin) {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
//
//    private fun login(inputEmail: String, inputPassword: String) {
//        showLoading(true)
//
//        val client = ApiConfig.getApiService().login(inputEmail, inputPassword)
//        client.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                showLoading(false)
//                val responseBody = response.body()
//                Log.d(TAG, "onResponse: $responseBody")
//                if (response.isSuccessful && responseBody?.message == "success") {
//                    loginViewModel.saveUser(User(responseBody.loginResult.token, true))
//                    Toast.makeText(
//                        this@LoginActivity,
//                        getString(R.string.login_success),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Log.e(TAG, "onFailure1: ${response.message()}")
//                    Toast.makeText(
//                        this@LoginActivity,
//                        getString(R.string.login_fail),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                showLoading(false)
//                Log.e(TAG, "onFailure2: ${t.message}")
//                Toast.makeText(
//                    this@LoginActivity,
//                    getString(R.string.login_fail),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        })
//    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Login Activity"
    }
}
