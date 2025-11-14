package com.example.and2.homework.and.homework.s01.adapter

import com.example.and2.homework.and.homework.s01.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onShare(post: Post)
    fun onClick(post: Post)
}