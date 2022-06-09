package com.example.jualbukuid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.jualbukuid.R
import com.example.jualbukuid.databinding.ActivityLoginBinding
import com.example.jualbukuid.ui.api.ApiConfig
import com.example.jualbukuid.ui.model.User
import com.example.jualbukuid.ui.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
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
            val input_email = activityLoginBinding.etEmail.text.toString()
            val input_password = activityLoginBinding.etPassword.text.toString()

            login(input_email, input_password)
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
        client.enqueue(object : Callback<LoginResponse> {
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

