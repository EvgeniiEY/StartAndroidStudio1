package ru.netology.nmedia


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode


import java.text.DecimalFormat
import kotlin.math.abs


class MainActivity : AppCompatActivity() {


    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        var likeStatus = 0
        val countStringLikes = binding.likesNumber.text.toString()
        var counterL: Int = Integer.parseInt(countStringLikes)

        setContentView(binding.root)
        binding.likesButton.setOnClickListener {

            // ВЕТКА ВЫГРУЖЕНА
            if (likeStatus == 0) {
                binding.likesButton.setImageResource(R.drawable.ic_liked_24)
                likeStatus = 1
                counterL++
                binding.likesNumber.text = reductionInNumbers(counterL)



            } else {
                if (likeStatus == 1) {
                    binding.likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    likeStatus = 0
                    counterL--
                    binding.likesNumber.text = reductionInNumbers(counterL)
                }
            }
        }

        binding.repostsButton.setOnClickListener {


            val countStringReposts = binding.repostsNumber.text.toString()
            var counterR: Int = Integer.parseInt(countStringReposts)
            counterR++
            binding.repostsNumber.text = counterR.toString()


        }

        binding.avatar.setOnClickListener{

        }
        binding.root.setOnClickListener{
         println("сработал binding root")
        }
        binding.threeDotButton.setOnClickListener{

        }
//сработал клик на кнопке лайк

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
}

