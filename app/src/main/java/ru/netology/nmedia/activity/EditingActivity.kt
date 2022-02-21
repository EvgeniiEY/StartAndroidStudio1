package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.FragmentEditPostBinding


class EditingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FragmentEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contentEditor.setText(intent?.getStringExtra(Intent.EXTRA_TEXT))

        binding.saveEdit.setOnClickListener {
            val text = binding.contentEditor.text.toString()
            val intent = Intent().apply { putExtra(Intent.EXTRA_TEXT, text) }
            setResult(RESULT_OK, intent)

            finish()
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
}


