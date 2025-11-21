package com.example.and2.homework.and.homework.s01.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.activity.EditPostResultContract
import com.example.and2.homework.and.homework.s01.activity.NewPostResultContract
import com.example.and2.homework.and.homework.s01.adapter.OnInteractionListener
import com.example.and2.homework.and.homework.s01.adapter.PostsAdapter
import com.example.and2.homework.and.homework.s01.databinding.FragmentFeedBinding
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel by viewModels<PostViewModel>()
    private lateinit var adapter: PostsAdapter
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        applyInsets(binding.root)

        val newPostLauncher = registerForActivityResult(NewPostResultContract) { result ->
            result ?: return@registerForActivityResult
            viewModel.savePost(result)
        }

        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result?.let {
                viewModel.savePost(result)
            } ?: viewModel.clear()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch(Unit)
        }
        setupAdapter(
            onEdit = { content ->
                editPostLauncher.launch(content)
            },
        )
        observeViewModel()
        return binding.root
    }

    private fun setupAdapter(
        onEdit: (content: String) -> Unit
    ) {
        adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
                onEdit(post.content)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))

                startActivity(shareIntent)
            }

            override fun onClick(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = post.videoUrl!!.toUri()
                }
                //val shareIntent = Intent.createChooser(intent, "Play Video")
                startActivity(intent)
            }

        })
        binding.list.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
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