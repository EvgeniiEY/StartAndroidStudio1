package ru.netology.nmedia


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


import java.text.DecimalFormat



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        var likeStatus = 0

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        likesButton.setOnClickListener {
            if (likeStatus == 1) {
                likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                likeStatus = 0
                val countStringLikes = likesNumber.text.toString()
                var counterL: Int = Integer.parseInt(countStringLikes)
                counterL--
                if(counterL <= 1000) {
                    likesNumber.text = counterL.toString()
                } else {likesNumber.text = "1K"}

            } else {
                likesButton.setImageResource(R.drawable.ic_liked_24)
                likeStatus = 1
                val countStringLikes = likesNumber.text.toString()
                var counterL: Int = Integer.parseInt(countStringLikes)
                counterL++
                likesNumber.text = counterL.toString()

            }
        }
    }


    fun countReposts(view: View) {
        val countStringReposts = repostsNumber.text.toString()
        var counterR: Int = Integer.parseInt(countStringReposts)
        counterR++
        repostsNumber.text = counterR.toString()
    }
}




//@Nullable
//public final <T extends View> T findViewById(@


//    fun showPopup(v: View?) {
//        val popup = PopupMenu(this, v)
//        val inflater = popup.menuInflater
//        inflater.inflate(R.menu.actions, popup.menu)
//        popup.show()
//    }
//}