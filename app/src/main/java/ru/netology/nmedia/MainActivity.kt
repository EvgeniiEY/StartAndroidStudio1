package ru.netology.nmedia

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)
        val listView = findViewById<ImageButton>(R.id.)
    }

@Nullable
public final <T extends View> T findViewById


//    fun showPopup(v: View?) {
//        val popup = PopupMenu(this, v)
//        val inflater = popup.menuInflater
//        inflater.inflate(R.menu.actions, popup.menu)
//        popup.show()
//    }
}