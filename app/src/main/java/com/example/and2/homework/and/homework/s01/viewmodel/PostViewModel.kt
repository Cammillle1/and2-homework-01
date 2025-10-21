package com.example.and2.homework.and.homework.s01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.and2.homework.and.homework.s01.repository.PostRepository
import com.example.and2.homework.and.homework.s01.repository.PostRepositoryImpl

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shares() = repository.shares()
    fun views() = repository.views()
}