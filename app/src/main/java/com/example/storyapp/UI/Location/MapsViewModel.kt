package com.example.storyapp.UI.Location

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.storyapp.DI.Injection
import com.example.storyapp.Data.StoryRepository
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.*
import kotlinx.coroutines.launch
import com.example.storyapp.Helper.Result

class MapsViewModel(private val pref: UserPreference, private val storyRepository: StoryRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStoryLocation(
        token: String,
        location : Int = 1,
        onSuccess: (List<ListStoryResult>) -> Unit
    ) = viewModelScope.launch {
            storyRepository.getStoryLocation(token, location).collect{ response ->
            when(response){
                is Result.Loading -> {
                    _isLoading.value = true
                }
                is Result.Success -> {
                    _isLoading.value = false
                    if(!response.data.error){
                        onSuccess(response.data.listStory)
                        Log.e("TEST",response.data.listStory.toString())
                    }
                }
                else -> {
                    _isLoading.value = false
                }
            }
        }
    }

    fun getUser(
        user: (LoginResult) -> Unit
    ) = viewModelScope.launch {
        pref.getUser().collect{
            user(it)
        }
    }
}
class MapViewModelFactory(private val pref: UserPreference,private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(pref, Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}