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
import com.example.jualbukuid.R
import com.example.jualbukuid.databinding.ActivityLoginBinding
import com.example.jualbukuid.models.SharedViewModel
import com.example.jualbukuid.response.LoginResponse
import com.example.jualbukuid.api.ApiConfig
import com.example.jualbukuid.models.User
import com.example.jualbukuid.models.UserPreference
import com.example.jualbukuid.models.ViewModelFactory
import retrofit2.Call
import retrofit2.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: SharedViewModel
    private lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        setup()



        activityLoginBinding.btnLogin.setOnClickListener {
            val inputEmail = activityLoginBinding.etEmail.text.toString()
            val inputPassword = activityLoginBinding.etPassword.text.toString()

            login(inputEmail, inputPassword)
        }
        activityLoginBinding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setup() {
        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login(inputEmail: String, inputPassword: String) {
        showLoading(true)

        val client = ApiConfig.getApiService().login(inputEmail, inputPassword)
        client.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                showLoading(false)
                val responseBody = response.body()
                Log.d(TAG, "onResponse: $responseBody")
                if (response.isSuccessful && responseBody?.message == "success") {
                    loginViewModel.saveUser(User(responseBody.loginResult.token, true))
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(
                        this@LoginActivity,
                        getString(R.string.login_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.login_fail),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityLoginBinding.progressBar.visibility = View.VISIBLE
        } else {
            activityLoginBinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Login Activity"
    }
}
