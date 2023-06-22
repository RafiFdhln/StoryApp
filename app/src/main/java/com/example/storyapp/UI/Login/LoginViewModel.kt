package com.example.storyapp.UI.Login

import androidx.lifecycle.*
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.ApiConfig
import com.example.storyapp.Network.LoginResult
import com.example.storyapp.Network.LoginUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _loginUser = MutableLiveData<LoginResult>()
    private val _loginError = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()

    val loginResult: LiveData<LoginResult> = _loginUser
    val loginError : LiveData<String> = _loginError
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveUser(user: LoginResult) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginUser> {
            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null && !response.body()!!.error) {
                        _loginUser.value = response.body()?.loginResult
                        _loginError.value = response.message()
                    }
                } else {
                    _loginError.value = response.message()
                }
            }
            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun isLogin(): LiveData<Boolean> {
        return pref.isLogin().asLiveData()
    }
}