package com.example.jualbukuid.models

import android.content.Context
import android.content.SharedPreferences

class SharedUserPreferences(context: Context) {
    private val sharedPref: SharedPreferences
    val putData: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        putData = sharedPref.edit()
    }

    fun setPref(key: String, value: Boolean){
        putData.putBoolean(key, value).apply()
    }

    fun saveName(name: String){
        putData.putString(PREF_NAME, name)
        putData.apply()
    }

    fun savePhone(phone: String){
        putData.putString(PREF_PHONE, phone)
        putData.apply()
    }

    fun saveEmail(email: String){
        putData.putString(PREF_EMAIL, email)
        putData.apply()
    }


    fun fetchName(): String?{
        return sharedPref.getString(PREF_NAME, null)
    }

    fun fetchPhone(): String?{
        return sharedPref.getString(PREF_PHONE, null)
    }

    fun getBoolean(key: String): Boolean?{
        return sharedPref.getBoolean(key, false)
    }


    companion object {
        const val PREFS_NAME = "user_pref"
        const val PREF_NAME = "Pref_Username"
        const val PREF_PHONE = "Pref_Phone_Number"
        const val PREF_EMAIL = "Pref_Email"
    }
}