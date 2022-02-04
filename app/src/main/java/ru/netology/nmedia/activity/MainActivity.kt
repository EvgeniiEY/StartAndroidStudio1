package ru.netology.nmedia.activity

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.net.Uri
import android.os.Bundle
import android.view.Gravity.apply
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R

import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels
import androidx.core.view.GravityCompat.apply
import androidx.core.view.isVisible
import ru.netology.nmedia.adapter.PostCallBack
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils
import java.security.acl.Group


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostContract()) { text ->
            text?.let {
                viewModel.changeContent(it)
                viewModel.save()
            }
        }
        val editingPostContract = registerForActivityResult(EditingPostContract()) { text ->
            text?.let {
                viewModel.changeContent(it)
                viewModel.save()
            }
        }

        val adapter = PostsAdapter(object : PostCallBack {

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    data = Uri.parse("link to an image")
                    type = "text/plain"
                }

                val chooser = Intent.createChooser(intent, getString(R.string.share_post))
                startActivity(chooser)
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

        viewModel.data.observe(this, { posts ->
            adapter.submitList(posts)
        })
        binding.add.setOnClickListener() {
            newPostContract.launch()
        }



        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }

            editingPostContract.launch(post.content)

//            binding.group.isVisible = true
//            binding.contentEditor.setText(post.content)
//            binding.editedTextPart.text = post.content
//
//            binding.contentEditor.requestFocus()
        }
        binding.cancelEditButton.setOnClickListener {
            binding.group.visibility = View.VISIBLE
            binding.contentEditor.setText("")
            Utils.hideKeyboard(it)
        }


//        binding.saveButton.setOnClickListener {
//            with(binding.contentEditor) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        R.string.error_empty_content,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//
//
//                binding.group.visibility = View.INVISIBLE
//                setText("")
//                clearFocus()
//                Utils.hideKeyboard(it)
//            }
//        }
    }
}



