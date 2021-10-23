package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val authorAvatar: String ?,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    val likes: Int,
    val share: Int,
    val views: Int,
    val video: String ?
    )