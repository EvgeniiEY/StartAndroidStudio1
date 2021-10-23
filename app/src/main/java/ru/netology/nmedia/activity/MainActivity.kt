package ru.netology.nmedia.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {


    @SuppressLint("StringFormatInvalid")

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

            }

            if (post.likedByMe) {
                likesButton?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }


            likesButton?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likesButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24
                    else R.drawable.ic_baseline_favorite_24
                )

                val counter = post.likes + 1
                if (post.likedByMe) likesNumber.text = reductionInNumbers(counter)
                else likesNumber.text = reductionInNumbers(post.likes)
            }

            repostsButton.setOnClickListener {
                val counter = post.share + 1
                repostsNumber.text = reductionInNumbers(counter)
            }
        }
    }
}

//        var likeStatus = 0
//        val countStringLikes = binding.likesNumber.text.toString()
//        var counterL: Int = Integer.parseInt(countStringLikes)
//        binding.likesButton.setOnClickListener {
//
//            // ВЕТКА ВЫГРУЖЕНА
//            if (likeStatus == 0) {
//                binding.likesButton.setImageResource(R.drawable.ic_liked_24)
//                likeStatus = 1
//                counterL++
//                binding.likesNumber.text = reductionInNumbers(counterL)
//          } else {
//                if (likeStatus == 1) {
//                    binding.likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                    likeStatus = 0
//                    counterL--
//                    binding.likesNumber.text = reductionInNumbers(counterL)
//                }
//            }
//        }

//        binding.repostsButton.setOnClickListener {
//
//
//            val countStringReposts = binding.repostsNumber.text.toString()
//            var counterR: Int = Integer.parseInt(countStringReposts)
//            counterR++
//            binding.repostsNumber.text = counterR.toString()
//
//
//        }
//        binding.likesButton.setOnClickListener {
//
//        }
//        binding.avatar.setOnClickListener {
//
//        }
//        binding.root.setOnClickListener {
//            println("сработал binding root")
//        }
//        binding.threeDotButton.setOnClickListener {
//
//        }
//сработал клик на кнопке лайк


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
//    }


