package com.example.and2.homework.and.homework.s01.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.repository.PostRepository
import com.example.and2.homework.and.homework.s01.repository.PostRepositoryFileImpl
import com.example.and2.homework.and.homework.s01.repository.PostRepositoryImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    likedByMe = false
)

class PostViewModel(app: Application) : AndroidViewModel(app) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryFileImpl(app)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun savePost(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content != text) {
                repository.save(it.copy(content = text))
            }
        }
        clear()
    }

    fun clear() {
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun sharesById(id: Long) = repository.sharesById(id)
    fun removeById(id: Long) = repository.deleteById(id)
}