package com.example.and2.homework.and.homework.s01.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.adapter.OnInteractionListener
import com.example.and2.homework.and.homework.s01.adapter.PostsAdapter
import com.example.and2.homework.and.homework.s01.databinding.ActivityMainBinding
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.util.AndroidUtils
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<PostViewModel>()
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        applyInsets(binding.root)
        setupAdapter()
        observeViewModel()
        setupListeners()
    }


    private fun setupAdapter() {
        adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.sharesById(post.id)
            }
        })
        binding.list.adapter = adapter

    }

    private fun observeViewModel() {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding.content) {
                    setText(post.content)
                    binding.close.visibility = View.VISIBLE
                    AndroidUtils.showKeyboard(this)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.save(text.toString())
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.close.visibility = View.INVISIBLE
            }
        }
        binding.close.setOnClickListener {
            binding.content.apply {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
            viewModel.clear()
            binding.close.visibility = View.INVISIBLE
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
}





