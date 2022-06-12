package com.example.jualbukuid.models

import androidx.lifecycle.*
import com.example.jualbukuid.api.ApiConfig
import com.example.jualbukuid.response.Data
import com.example.jualbukuid.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {

    val user = MutableLiveData<LoginResponse>()
    val dataUser = MutableLiveData<Data>()
    private lateinit var responseLogin: LoginResponse

    fun setLogin(email: String, pass: String){
        responseLogin = LoginResponse(data = null, null, null, email.toString(), pass.toString())
        val client = ApiConfig.getApiService().postLogin(responseLogin)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    login()
                    val data = response.body()?.data
                    if (data != null) {
                        saveUser(
                            User(
                                data.email,
                                data.pass,
                                data.name,
                                data.phone,
                                true
                            )
                        )
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            }
        })
    }

    fun setData(email: String, pass: String){
        responseLogin = LoginResponse(data = null, null, null, email.toString(), pass.toString())
        val client = ApiConfig.getApiService().postLogin(responseLogin)
        client.enqueue(object : retrofit2.Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    val result = response.body()?.data
                    if (result != null){
                        dataUser.postValue(result!!)
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getLogin(): LiveData<LoginResponse>{
        return user
    }

    fun getData(): LiveData<Data>{
        return dataUser
    }


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user : User){
        viewModelScope.launch {
            pref.saveUser(User(user.email, user.pass, user.name, user.phone, user.isLogin))
        }
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

}