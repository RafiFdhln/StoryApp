package com.example.storyapp.Data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.storyapp.Database.StoryDatabase
import com.example.storyapp.Helper.Result
import com.example.storyapp.Network.ApiService
import com.example.storyapp.Network.ListStoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getStory(token: String): LiveData<PagingData<ListStoryResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getStoryLocation(token: String, location: Int?) = flow {
        emit(Result.Loading)
        val response = apiService.getAllStories("Bearer $token", location = location)
        response.let {
            if (!it.error) emit(Result.Success(it))
            else emit(Result.Error(it.message))
        }
    }.catch {
        emit(Result.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}