package com.example.jualbukuid.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val isLogin: Boolean,
) : Parcelable
