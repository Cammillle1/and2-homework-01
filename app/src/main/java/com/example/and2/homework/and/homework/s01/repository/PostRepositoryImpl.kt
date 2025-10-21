package com.example.and2.homework.and.homework.s01.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.dto.Post

class PostRepositoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        authorId = R.string.title,
        contentId = R.string.content,
        published = "21 мая в 18:36",
        likes = 999,
        shares = 999,
        views = 999,
        likedByMe = false
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        data.value = post
    }

    override fun shares() {
        post = post.copy(
            shares = post.shares + 1
        )
        data.value = post
    }

    override fun views() {
        post = post.copy(
            views = post.views + 1
        )
        data.value = post
    }
}