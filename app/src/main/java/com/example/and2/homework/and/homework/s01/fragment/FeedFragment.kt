package com.example.and2.homework.and.homework.s01.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.adapter.OnInteractionListener
import com.example.and2.homework.and.homework.s01.adapter.PostsAdapter
import com.example.and2.homework.and.homework.s01.databinding.FragmentFeedBinding
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.fragment.NewPostFragment.Companion.textArg
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private lateinit var adapter: PostsAdapter
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        setupAdapter(
            onEdit = { content ->
                findNavController().navigate(
                    R.id.action_feedFragment_to_editPostFragment,
                    Bundle().apply {
                        textArg = content
                    })
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

            override fun onVideoClick(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_VIEW
                    data = post.videoUrl!!.toUri()
                }
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
}