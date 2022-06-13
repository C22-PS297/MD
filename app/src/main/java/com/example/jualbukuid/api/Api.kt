package com.example.jualbukuid.api


import com.example.jualbukuid.response.LoginResponse
import com.example.jualbukuid.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @Headers("Content-Type: application/json")
    @POST("/user")
    fun getStringScalar(@Body body: RegisterResponse): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("/user/login")
    fun postLogin(@Body body: LoginResponse): Call<LoginResponse>


}