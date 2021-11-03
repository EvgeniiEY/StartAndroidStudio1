package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R

import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostCallBack
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter (object : PostCallBack {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

        })
        binding.mainList.adapter = adapter
        binding.mainList.itemAnimator = null

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}



