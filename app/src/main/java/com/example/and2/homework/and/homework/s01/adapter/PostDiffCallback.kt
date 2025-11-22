package com.example.and2.homework.and.homework.s01.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.and2.homework.and.homework.s01.model.Post

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem
    }
}