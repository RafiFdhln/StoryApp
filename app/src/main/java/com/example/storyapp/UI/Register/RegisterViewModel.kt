package com.example.storyapp.UI.Register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.Network.ApiConfig
import com.example.storyapp.Network.RegisterUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _loginMessage = MutableLiveData<String>()
    private val _loginError = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()

    val loginMessage: LiveData<String> = _loginMessage
    val loginError : LiveData<Boolean> = _loginError
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(name, email, password)
        client.enqueue(object : Callback<RegisterUser> {
            override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                        _loginError.value = true
                        _loginMessage.value = response.message()

                } else {
                    _loginMessage.value = response.message()
                }
            }
            override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                Log.e("Register", "onFailure1: ${t.message.toString()}")
                _isLoading.value = false
            }
        })
    }
}