package com.example.and2.homework.and.homework.s01.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Long = 0,
    val shares: Long = 0,
    val views: Long = 0,
    val likedByMe: Boolean = false
)