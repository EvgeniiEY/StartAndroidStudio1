package ru.netology.nmedia.activity

import android.os.Bundle
import android.widget.Toast
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

        val adapter = PostsAdapter(object : PostCallBack {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun edit(post: Post) {
                viewModel.edit(post)
            }

        })
        binding.mainList.adapter = adapter
        binding.mainList.itemAnimator = null

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if(post.id == 0L) {
                return@observe
            }
            binding.content.setText(post.content)
            binding.content.requestFocus()
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if(text.isNullOrBlank()) {
                Toast.makeText(this@MainActivity, R.string.error_empty_content, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                Utils.hideKeyboard(it)
            }
        }
    }
}



