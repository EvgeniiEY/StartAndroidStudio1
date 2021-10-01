package ru.netology.nmedia


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode


class MainActivity : AppCompatActivity() {


    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        var likeStatus = 0
        val countStringLikes = binding.likesNumber.text.toString()
        var counterL: Double = countStringLikes.toDouble()
        setContentView(binding.root)
        binding.likesButton.setOnClickListener {



            if (likeStatus == 0) {
                binding.likesButton.setImageResource(R.drawable.ic_liked_24)
                likeStatus = 1
                counterL++

                if (Math.abs(counterL / 1_000) >= 1 && Math.abs(counterL / 1_000) < 1000) {
                    val counterLrounded =
                        (counterL / 1_000).toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()
                    binding.likesNumber.text =
                        getString(R.string.app_likes, (counterLrounded).toString(), "k")


                } else if (Math.abs(counterL / 1_000_000) >= 1) {
                    val counterLrounded =
                        (counterL / 1_000_000).toBigDecimal().setScale(1, RoundingMode.UNNECESSARY)
                            .toDouble()
                    binding.likesNumber.text =
                        getString(R.string.app_likes, (counterLrounded).toString(), "m")
                } else {
                    val countStringLikes = binding.likesNumber.text.toString()
                    var counterL: Int = Integer.parseInt(countStringLikes)

                    binding.likesNumber.text = counterL.toString()


                }

            } else {
                if (likeStatus == 1) {
                    binding.likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    likeStatus = 0
                    counterL--
                    if (Math.abs(counterL / 1_000) >= 1 && Math.abs(counterL / 1_000) < 1000) {
                        counterL--
                        val counterLrounded =
                            (counterL / 1_000).toBigDecimal().setScale(0, RoundingMode.HALF_DOWN)
                                .toDouble()
                        binding.likesNumber.text =
                            getString(R.string.app_likes, (counterLrounded).toString(), "k")

                    } else if (Math.abs(counterL / 1_000_000) >= 1) {
                        val counterLrounded =
                            (counterL / 1_000_000).toBigDecimal().setScale(1, RoundingMode.FLOOR)
                                .toDouble()
                        binding.likesNumber.text =
                            getString(R.string.app_likes, (counterLrounded).toString(), "m")
                    } else {
                        val countStringLikes = binding.likesNumber.text.toString()
                        var counterL: Int = Integer.parseInt(countStringLikes)

                        binding.likesNumber.text = counterL.toString()
                    }
                }
            }
        }

        binding.repostsButton.setOnClickListener {


            val countStringReposts = binding.repostsNumber.text.toString()
            var counterR: Int = Integer.parseInt(countStringReposts)
            counterR++
            binding.repostsNumber.text = counterR.toString()


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