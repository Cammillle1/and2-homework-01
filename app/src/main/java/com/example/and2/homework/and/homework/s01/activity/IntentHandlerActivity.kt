package com.example.and2.homework.and.homework.s01.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.databinding.ActivityIntentHandlerBinding
import com.example.and2.homework.and.homework.s01.util.AndroidUtils
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar

class IntentHandlerActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntentHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        applyInsets(binding.root)

        AndroidUtils.showKeyboard(binding.edit)

        intent?.let {
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root, R.string.error_empty_content,
                    LENGTH_INDEFINITE
                )
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
                return@let
            }
            binding.edit.setText(text)

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
}

private fun applyInsets(root: View) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        // Для клавиатуры:
        val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
        val isImeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
        /*
            Установка отступов
            При повторном использовании у v будут отступы заданные ранее,
            которые могут уже включать системные отступы,
            в таком случае добавлять их ещё раз не нужно,
            так как это приведёт к увеличению отступов
         */
        v.setPadding(
            v.paddingLeft,
            systemBars.top,
            v.paddingRight,
            if (isImeVisible) imeInsets.bottom else systemBars.bottom
        )
        insets
    }
}