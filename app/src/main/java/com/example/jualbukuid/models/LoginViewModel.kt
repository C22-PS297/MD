package com.example.jualbukuid.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jualbukuid.api.ApiConfig
import com.example.jualbukuid.response.LoginResponse
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    val userLogin = MutableLiveData<LoginResponse>()
    private lateinit var responses: LoginResponse

    fun setLogin(email: String, pass: String){
        responses = LoginResponse(error = null, message = null, email, pass)
        val client = ApiConfig.getApiService().postLogin(responses)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    userLogin.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getLogin(): LiveData<LoginResponse>{
        return userLogin
    }
}