package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditingBinding
import ru.netology.nmedia.dto.Post


class EditingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.share_post))
            startActivity(shareIntent)

            intent?.let {
                if (it.action != Intent.ACTION_SEND) {
                    return@let
                }

                val text = it.getStringExtra(Intent.EXTRA_TEXT)
                if (text.isNullOrBlank()) {
                    Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok) {
                            finish()
                        }
                        .show()
                    return@let

                }
            }
        }
    }
}

