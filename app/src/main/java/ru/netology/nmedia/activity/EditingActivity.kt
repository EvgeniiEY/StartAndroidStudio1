package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditingBinding
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding.inflate
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.databinding.CardPostBinding.inflate
import ru.netology.nmedia.dto.Post


class EditingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = post(con
        setContentView(binding.root)
        binding.save.setOnClickListener {
            val text = binding.contentEditor.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val intent = Intent().apply { putExtra(Intent.EXTRA_TEXT, text) }
                setResult(RESULT_OK, intent)
            }
            finish()
        }



    }
}

