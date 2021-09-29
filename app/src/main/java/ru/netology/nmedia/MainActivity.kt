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
        var counterL: Double = countStringLikes.toDouble()
        setContentView(binding.root)
        binding.likesButton.setOnClickListener {

        // ВЕТКА ВЫГРУЖЕНА
            if (likeStatus == 0) {
                binding.likesButton.setImageResource(R.drawable.ic_liked_24)
                likeStatus = 1
                counterL++

                if (Math.abs(counterL / 1_000) >= 1) {
                    val counterLrounded = (counterL/1000).toBigDecimal().setScale(1, RoundingMode.FLOOR).toDouble()
                    binding.likesNumber.text = getString(R.string.app_likes, (counterLrounded).toString(), "k")

                } else if (Math.abs(counterL / 1_000_000) >= 1) {
                    binding.likesNumber.text = getString(R.string.app_likes, (counterL / 1_000_000).toString(), "m")
                } else {
                    binding.likesNumber.text = getString(R.string.app_likes, counterL.toString(), "")

                }



        } else {
            if (likeStatus == 1) {
                binding.likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                likeStatus = 0
//                    val countStringLikes = likesNumber.text.toString()
//                    var counterL: Int = Integer.parseInt(countStringLikes)
                counterL--
                when {
                    abs(counterL / 1000000) > 1 -> {
                        binding.likesNumber.text = (counterL / 1000000).toString()
                        getString(R.string.app_likes, binding.likesNumber.text, "k")
                    }
                    abs(counterL / 1000) > 1 -> {
                        binding.likesNumber.text = (counterL / 1000).toString()
                        getString(R.string.app_likes, binding.likesNumber.text, "k")
                    }
                    else -> {
                        binding.likesNumber.text = counterL.toString()
                    }
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

//android:onClick= "countReposts"

//@Nullable
//public final <T extends View> T findViewById(@


//    fun showPopup(v: View?) {
//        val popup = PopupMenu(this, v)
//        val inflater = popup.menuInflater
//        inflater.inflate(R.menu.actions, popup.menu)
//        popup.show()
//    }
//}