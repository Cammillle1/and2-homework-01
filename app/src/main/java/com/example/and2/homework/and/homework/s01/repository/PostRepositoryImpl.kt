package com.example.and2.homework.and.homework.s01.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.dto.Post

class PostRepositoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 1,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 1000,
            shares = 10000,
            likedByMe = false
        ),
        Post(
            id = 2,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 30,
            likedByMe = false
        ),
        Post(
            id = 3,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 90,
            likedByMe = false
        ),
        Post(
            id = 4,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 90,
            likedByMe = false
        ),
        Post(
            id = 5,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 90,
            likedByMe = false
        ),
        Post(
            id = 6,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 90,
            likedByMe = false
        ),
        Post(
            id = 7,
            authorId = R.string.title,
            contentId = R.string.content,
            published = "23 сентября в 10:12",
            likes = 100,
            likedByMe = false
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map { it ->
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun sharesById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shares = it.shares + 1
            )
        }
        data.value = posts
    }

    override fun views() {
        TODO("Not yet implemented")
    }


}