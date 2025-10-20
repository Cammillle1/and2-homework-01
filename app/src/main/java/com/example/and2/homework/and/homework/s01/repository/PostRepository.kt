package com.example.and2.homework.and.homework.s01.repository

import androidx.lifecycle.LiveData
import com.example.and2.homework.and.homework.s01.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()

    fun shares()

    fun views()
}