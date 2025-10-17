package com.example.and2.homework.and.homework.s01.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Long = 0,
    var shares: Long = 0,
    var views: Long = 0,
    var likedByMe: Boolean = false
)