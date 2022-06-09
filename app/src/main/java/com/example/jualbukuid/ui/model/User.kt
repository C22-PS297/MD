package com.example.jualbukuid.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val token: String,
    val isLogin: Boolean,
) : Parcelable
