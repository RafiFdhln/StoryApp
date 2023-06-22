package com.example.storyapp.UI.Home

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.DI.Injection
import com.example.storyapp.Data.StoryRepository
import com.example.storyapp.Helper.UserPreference
import com.example.storyapp.Network.ListStoryResult
import com.example.storyapp.Network.LoginResult
import kotlinx.coroutines.launch

class HomeViewModel(private val pref: UserPreference, private val storyRepository: StoryRepository): ViewModel(){
    fun getStory(token: String): LiveData<PagingData<ListStoryResult>> {
        return storyRepository.getStory("Bearer $token").cachedIn(viewModelScope)
    }

    fun getUser(
        user: (LoginResult) -> Unit
    ) = viewModelScope.launch {
        pref.getUser().collect{
            user(it)
        }
    }

    fun logout() = viewModelScope.launch {
        pref.logout()
    }
}

class HomeViewModelFactory(private val pref: UserPreference,private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(pref, Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}