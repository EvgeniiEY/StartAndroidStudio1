package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.utils.Utils


interface PostCallBack {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
    fun playVideo(post: Post)
    fun onCLick(post: Post)
}


class PostsAdapter(private val postCallBack: PostCallBack) :
    ListAdapter<Post, PostViewHolder>(PostsDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, postCallBack)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val postCallBack: PostCallBack
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            likesButton.text = post.likes.toString()
            repostsButton.text = post.share.toString()
            likesButton.isChecked = post.likedByMe
            likesButton.text = Utils.reductionInNumbers(post.likes)
            repostsButton.text = Utils.reductionInNumbers(post.share)
            likesButton.setOnClickListener {

                postCallBack.onLike(post)
            }
            repostsButton.setOnClickListener {
                postCallBack.onShare(post)
            }
            threeDotButton.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_options)

                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {

                            R.id.post_remove -> {
                                postCallBack.remove(post)

                                true

                            }
                            R.id.post_edit -> {
                                postCallBack.edit(post)


                                true


                            }
                            else -> false
                        }
                    }
                }.show()
            }

            content.setOnClickListener {
                postCallBack.onCLick(post)
            }



            playVideoButton.setOnClickListener {
                postCallBack.playVideo(post)

            }
        }
    }
}


class PostsDiffCallBack : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }


}


class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {

            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
}







