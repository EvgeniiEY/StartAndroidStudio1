package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditingBinding.inflate
import ru.netology.nmedia.databinding.ActivityPlayVideoBinding
import ru.netology.nmedia.databinding.CardPostBinding.inflate

class PlayVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
    }
}