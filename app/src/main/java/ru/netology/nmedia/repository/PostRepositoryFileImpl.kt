package ru.netology.nmedia.repository;

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post


class PostRepositoryFileImpl(val context: Context) : PostRepository {

    private var gson = Gson()
    private var type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "posts.json"
    private var nextId = 1L
    private var posts = emptyList<Post>()

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(filename)
        if (file.exists()) {
            context.openFileInput(filename).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextId = posts.maxOfOrNull { post -> post.id }?.inc() ?: 1L
                data.value = posts
            }
        } else {
            sync()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
        sync()
    }



    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
        sync()
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }


    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))

        }
    }
}

//
//
//
//
//        ),
//    ).reversed()
//
//    private val data = MutableLiveData(posts)
//
//
//
//    override fun likeById(id: Long) {
//        posts = posts.map {
//
//            if (it.id != id) it
//            else if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1)
//            else it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
//        }
//        data.value = posts
//    }
//
//
//    override fun shareById(id: Long) {
//        posts = posts.map {
//            if (it.id != id) it else it.copy(share = it.share + 1)
//        }
//        data.value = posts
//    }
//
//
//
//    override fun save(post: Post) {
//        posts = if (post.id == 0L) {
//            listOf(post.copy(id = nextId++)) + posts
//        } else {
//            posts.map {
//                if (it.id != post.id) it
//                else it.copy(content = post.content)
//            }
//        }
//        data.value = posts
//
//    }
//
//    override fun playVideo(id: Long) {
//        data.value = posts
//    }
//
//
//}



