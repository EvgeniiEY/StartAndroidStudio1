package ru.netology.nmedia.repository;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология - университет интернет-профессий будущего.",
        authorAvatar = "Avatar",
        content = "Привет, это Нетология. Мы начинали с курсов об онлайн-маркетинге. Затем выросли в университет интернет-профессий: учили дизайнеров, аналитиков, программистов, менеджеров, маркетологов… Но обучать новым профессиям — это не предел. Мы продолжаем расти. Сегодня мы даём знания не только начинающим, но и тем, кто давно в профессии. Специалисты изучают новые инструменты, топ-менеджеры — получают степень MBA, руководители бизнеса — обучают своих сотрудников и обучаются сами. Нетология помогает расти на всех этапах карьеры — получать знания на старте и открывать новые высоты.",
        published = "21 мая в 18:36",
        likedByMe = false,
        likes = 999,
        share = 888,
        views = 666,
        video = "VHS"
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1
            else post.likes + 1
        )
        data.value = post
    }
    override fun share() {
        post = post.copy(
        share = post.share + 1
        )
        data.value = post
    }
}
