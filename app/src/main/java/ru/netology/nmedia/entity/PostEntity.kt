package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post


@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String?,
    val content: String = "",
    val published: String,
    val likedByMe: Boolean = false,
    val likes: Int = 0,
    val share: Int = 0,
    val shareById: Boolean = false,
    val views: Int = 0,
    val video: String?
) {
    fun toDto() = Post(id,author,authorAvatar,content,published,likedByMe,likes,share, shareById, views,video)

    companion object {
        fun fromDto(post:Post) = with(post) {
            PostEntity(id, author, authorAvatar, content, published,likes = 0, likedByMe = false, share = 0, shareById = false, views = 0,video = "" )
        }
    }
}