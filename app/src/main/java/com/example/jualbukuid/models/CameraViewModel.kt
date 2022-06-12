package com.example.jualbukuid.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jualbukuid.api.ApiStorageConfig
import com.example.jualbukuid.response.PhotoResponses
import retrofit2.Call
import retrofit2.Response

class CameraViewModel: ViewModel() {
    val photo = MutableLiveData<PhotoResponses>()

    fun sendPhoto(url: String){
        val client = ApiStorageConfig.getApiService().sendPhoto(url)
            .enqueue(object: retrofit2.Callback<PhotoResponses>{
                override fun onResponse(
                    call: Call<PhotoResponses>,
                    response: Response<PhotoResponses>
                ) {
                    if (response.isSuccessful){
                        photo.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PhotoResponses>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun getPhoto(): LiveData<PhotoResponses>{
        return photo
    }
}