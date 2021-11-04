package ru.netology.nmedia.repository;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология - университет интернет-профессий будущего.",
            authorAvatar = "Avatar",
            content = "Привет, это Нетология. Мы начинали с курсов об онлайн-маркетинге. Затем выросли в университет интернет-профессий: учили дизайнеров, аналитиков, программистов, менеджеров, маркетологов… Но обучать новым профессиям — это не предел. Мы продолжаем расти. Сегодня мы даём знания не только начинающим, но и тем, кто давно в профессии. Специалисты изучают новые инструменты, топ-менеджеры — получают степень MBA, руководители бизнеса — обучают своих сотрудников и обучаются сами. Нетология помогает расти на всех этапах карьеры — получать знания на старте и открывать новые высоты.",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 990,
            share = 888,
            views = 666,
            video = "VHS"
        ),
        Post(
            id = 2,
            author = "Нетология - университет интернет-профессий будущего.",
            authorAvatar = "Avatar",
            content = "ПОСТ №2 Привет, это Нетология. Мы начинали с курсов об онлайн-маркетинге. Затем выросли в университет интернет-профессий: учили дизайнеров, аналитиков, программистов, менеджеров, маркетологов… Но обучать новым профессиям — это не предел. Мы продолжаем расти. Сегодня мы даём знания не только начинающим, но и тем, кто давно в профессии. Специалисты изучают новые инструменты, топ-менеджеры — получают степень MBA, руководители бизнеса — обучают своих сотрудников и обучаются сами. Нетология помогает расти на всех этапах карьеры — получать знания на старте и открывать новые высоты.",
            published = "22 мая в 18:00",
            likedByMe = false,
            likes = 999,
            share = 999,
            views = 6666,
            video = "VHS_2"
        ),
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(likedByMe = !it.likedByMe, likes = it.likes + 1)
        }
        data.value = posts
    }
//поправить unlike
    override fun unlikeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(likedByMe = !it.likedByMe, likes = it.likes - 1 )
        }
        data.value = posts
    }



    override fun shareById(id: Long) {
        posts = posts.map{
            if(it.id != id ) it else it.copy(share = it.share + 1)
        }
        data.value = posts
    }


}


