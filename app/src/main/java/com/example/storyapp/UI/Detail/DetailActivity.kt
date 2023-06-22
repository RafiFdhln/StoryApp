package com.example.storyapp.UI.Detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.storyapp.Network.ListStoryResult
import com.example.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }
        setView()
    }
    private fun setView(){
        val myData = intent.extras?.getParcelable<ListStoryResult>("STORY")
        binding.apply {
            if (myData != null) {
                tvName.text = myData.name
                tvDesc.text = myData.description
                Glide.with(this@DetailActivity)
                    .load(myData.photoUrl)
                    .apply(RequestOptions().override(55, 55))
                    .into(binding.imgStories)
            }
        }
    }
}