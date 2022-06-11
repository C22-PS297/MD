package com.example.jualbukuid.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("status")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("phone")
    val phone: String? = null,
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("pass")
    val pass: String? = null

)
