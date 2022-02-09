package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
import ru.netology.nmedia.repository.PostRepositorySharedPrefsImpl


private val empty = Post(
     id = 0,
 author = "",
 authorAvatar = "",
 content = "",
 published = "",
 likedByMe = false,
 likes = 0,
 share = 0,
 views = 0,
 video = ""
)
class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySharedPrefsImpl(application)
    val data: LiveData<List<Post>> = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post

    }



    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if(it.content!=text) {
                edited.value = it.copy(content = text)
            }
        }
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun playVideoById(id:Long) = repository.playVideo(id)





}