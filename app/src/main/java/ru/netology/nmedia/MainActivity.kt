package ru.netology.nmedia


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import ru.netology.nmedia.databinding.ActivityMainBinding


import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        var likeStatus = 0
        val countStringLikes = binding.likesNumber.text.toString()
        var counterL: Int = Integer.parseInt(countStringLikes)
        setContentView(binding.root)
        binding.likesButton.setOnClickListener {


            if (likeStatus == 0) {
                binding.likesButton.setImageResource(R.drawable.ic_liked_24)
                likeStatus = 1
                counterL++
                if (counterL < 1000) {
                    binding.likesNumber.text = counterL.toString()


                } else if(counterL in 1000..1099){
                    binding.likesNumber.text = "1K"


            } else {
                if (likeStatus == 1) {
                    binding.likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    likeStatus = 0
//                    val countStringLikes = likesNumber.text.toString()
//                    var counterL: Int = Integer.parseInt(countStringLikes)
                    counterL--
                    binding.likesNumber.text = counterL.toString()
                    if (counterL < 1000) {
                        binding.likesNumber.text = counterL.toString()
                    } else {
                        binding.likesNumber.text = "1K"
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