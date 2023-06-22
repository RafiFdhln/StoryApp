package com.example.storyapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.Network.ListStoryResult
import com.example.storyapp.databinding.ItemStoryBinding

class StoryAdapter(private val onClick: (ListStoryResult) ->  Unit) :
    PagingDataAdapter<ListStoryResult, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding,onClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, onClick)
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding, onClick: (ListStoryResult) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ListStoryResult, onClick: (ListStoryResult) -> Unit) {
            binding.tvName.text = data.name
            Glide.with(binding.root)
                .load(data.photoUrl)
                .error(android.R.color.darker_gray)
                .into(binding.imgStories)
            binding.root.setOnClickListener {
                onClick(data)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryResult>() {
            override fun areItemsTheSame(oldItem: ListStoryResult, newItem: ListStoryResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryResult, newItem: ListStoryResult): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}