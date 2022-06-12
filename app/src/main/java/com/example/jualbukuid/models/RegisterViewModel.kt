package com.example.jualbukuid.models

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jualbukuid.api.ApiConfig
import com.example.jualbukuid.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    val userRegister = MutableLiveData<RegisterResponse>()
    private lateinit var responses: RegisterResponse

    fun setRegis(name: String, phone: String, email: String, pass: String){
        Log.d("name", name)
        Log.d("phone", phone)
        Log.d("email", email)
        Log.d("pass", pass)

        responses = RegisterResponse(error = null, message = null, name, phone, email, pass)
        val client = ApiConfig.getApiService().getStringScalar(responses)
        client.enqueue(object : retrofit2.Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful){
                    userRegister.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getRegis(): LiveData<RegisterResponse>{
        return userRegister
    }
}