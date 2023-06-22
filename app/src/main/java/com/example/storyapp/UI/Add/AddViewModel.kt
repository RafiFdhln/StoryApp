package com.example.storyapp.UI.Add

import androidx.lifecycle.*
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddViewModel(private val pref: UserPreference): ViewModel() {
    private val _addSuccess = MutableLiveData<Boolean>()
    private val _isLoading = MutableLiveData<Boolean>()

    val addSuccess: LiveData<Boolean> = _addSuccess
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadStory(
        token: String,
        imageMultipart: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody? = null,
        lon: RequestBody? = null,
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().uploadImage( "Bearer ${token}",imageMultipart, description,lat,lon)
        client.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(call: Call<AddStoryResponse>, response: Response<AddStoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _addSuccess.value = response.body()?.error
                }
            }
            override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getUser(
        user: (LoginResult) -> Unit
    ) = viewModelScope.launch {
        pref.getUser().collect{
            user(it)
        }
    }
}