package com.example.and2.homework.and.homework.s01.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.adapter.PostsAdapter
import com.example.and2.homework.and.homework.s01.databinding.ActivityMainBinding
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + v.paddingLeft,
                systemBars.top + v.paddingTop,
                systemBars.right + v.paddingRight,
                systemBars.bottom + v.paddingBottom
            )
            insets
        }
        setupAdapter()
    }

    private fun setupAdapter() {
        val adapter = PostsAdapter(onLikeListener = { post ->
            viewModel.likeById(post.id)
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}


private fun formatNumberShortPrecise(number: Long): String {
    return when {
        number < 10_000 -> {
            if (number < 1000) {
                number.toString()
            } else {
                val value = number.toDouble() / 1000
                "%.1fK".format(value)
            }
        }

        number < 1_000_000 -> {
            "${number / 1000}K"
        }

        else -> {
            val value = number.toDouble() / 1_000_000
            "%.1fM".format(value)
        }
    }
}