package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {


                author.text = post.author
                content.text = post.content
                published.text = post.published
                likesNumber.text = post.likes.toString()
                repostsNumber.text = post.share.toString()

                likesButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24
                    else R.drawable.ic_baseline_favorite_24

                )
                likesNumber.text = reductionInNumbers(post.likes)
                repostsNumber.text = reductionInNumbers(post.share)
            }
        }


        binding.likesButton.setOnClickListener {
            viewModel.like()
        }

        binding.repostsButton.setOnClickListener {
            viewModel.share()
        }
    }
}


fun reductionInNumbers(count: Int): String {
    val formatCount = when {
        count in 1000..9999 -> {
            String.format("%.1fK", count / 1000.0)
        }
        count in 10000..999999 -> {
            String.format("%dK", count / 1000)
        }
        count > 1000000 -> {
            String.format("%.1fM", count / 1000000.0)
        }

        else -> {
            count.toString()
        }
    }
    return formatCount
}



