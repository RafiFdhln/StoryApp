package com.example.storyapp.DI

import android.content.Context
import com.example.storyapp.Data.StoryRepository
import com.example.storyapp.Database.StoryDatabase
import com.example.storyapp.Network.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}