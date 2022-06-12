package com.example.jualbukuid.api

import com.example.jualbukuid.response.PhotoResponses
import retrofit2.Call
import retrofit2.http.*

interface ApiStorage {
    @Multipart
    @Headers("Content-Type: application/form-data")
    @POST("predict")
    fun sendPhoto(
        @Part("url")url: String
    ): Call<PhotoResponses>
}