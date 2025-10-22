package com.example.and2.homework.and.homework.s01.repository

import androidx.lifecycle.LiveData
import com.example.and2.homework.and.homework.s01.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)

    fun sharesById(id: Long)

    fun save(post: Post)
    fun deleteById(id: Long)
}