package com.example.and2.homework.and.homework.s01.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.databinding.ActivityNewPostBinding
import com.example.and2.homework.and.homework.s01.util.AndroidUtils

class NewPostActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        AndroidUtils.showKeyboard(binding.edit)

        binding.ok.setOnClickListener {
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED)
            } else {
                val intent = Intent()
                val content = binding.edit.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }


    }
}