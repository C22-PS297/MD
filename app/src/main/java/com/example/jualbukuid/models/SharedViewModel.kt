package com.example.jualbukuid.models

import android.net.Uri
import androidx.lifecycle.*
import com.example.jualbukuid.models.User
import kotlinx.coroutines.launch
import java.io.File
import java.net.URI

class SharedViewModel: ViewModel() {
    val _myFile = MutableLiveData<String>()
    var myFile = _myFile


    fun setFile(myFile: String){
        _myFile.value = myFile
    }

    fun getFile(): LiveData<String>{
        return myFile
    }
}