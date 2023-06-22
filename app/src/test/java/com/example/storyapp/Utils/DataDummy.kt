package com.example.storyapp.Utils

import com.example.storyapp.Network.ListStoryResult

object DataDummy {
    fun generateDummyStory(): List<ListStoryResult> {
        val item = arrayListOf<ListStoryResult>()
        for (i in 0 until 10) {
            val story = ListStoryResult(
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Dimas",
                "Lorem Ipsum",
                "story-FvU4u0Vp2S3PMsFg",
                -10.212,
                -16.002
            )
            item.add(story)
        }
        return item
    }
}