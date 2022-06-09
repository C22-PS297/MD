package com.example.jualbukuid.ui.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse2(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)