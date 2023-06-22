package com.example.storyapp.Database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyapp.Network.ListStoryResult

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryResult>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, ListStoryResult>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}