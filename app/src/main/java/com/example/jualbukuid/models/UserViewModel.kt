package com.example.jualbukuid.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private var preference: UserPreference): ViewModel() {
    fun getUser(): LiveData<User> {
        return preference.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            preference.logout()
        }
    }
}