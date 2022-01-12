package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils


interface PostCallBack {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun remove(post: Post)
    fun edit(post: Post)
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

            likesButton.setOnClickListener {
                PostCallBack.onLike(post)
            }
            repostsButton.setOnClickListener {
                PostCallBack.onShare(post)
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