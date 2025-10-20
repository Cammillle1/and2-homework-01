package com.example.and2.homework.and.homework.s01

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.and2.homework.and.homework.s01.databinding.ActivityMainBinding
import com.example.and2.homework.and.homework.s01.dto.Post

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val post = Post(
            id = 1,
            author = getString(R.string.title),
            content = getString(R.string.content),
            published = "21 мая в 18:36",
            likes = 999,
            shares = 999,
            views = 999,
            likedByMe = false
        )
        setupRoot(post)
        setupLikes(post)
        setupShares(post)
        setupViews(post)
    }

    private fun setupRoot(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_liked)
            }
            likesCount.text = post.likes.toString()
            sharesCount.text = post.shares.toString()
            viewsCount.text = post.views.toString()
        }
    }

    private fun setupLikes(post: Post) {
        with(binding) {
            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like
                )
                if (post.likedByMe) post.likes++ else post.likes--

                val newCount = formatNumberShortPrecise(post.likes)
                likesCount.text = newCount
            }
        }
    }

    private fun setupShares(post: Post) {
        with(binding) {
            shares.setOnClickListener {
                post.shares++
                val newCount = formatNumberShortPrecise(post.shares)
                sharesCount.text = newCount
            }
        }
    }

    private fun setupViews(post: Post) {
        with(binding) {
            views.setOnClickListener {
                post.views++
                val newCount = formatNumberShortPrecise(post.views)
                viewsCount.text = newCount
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
}