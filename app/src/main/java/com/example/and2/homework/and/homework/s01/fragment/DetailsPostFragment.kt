package com.example.and2.homework.and.homework.s01.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.adapter.OnInteractionListener
import com.example.and2.homework.and.homework.s01.databinding.FragmentDetailsPostBinding
import com.example.and2.homework.and.homework.s01.fragment.NewPostFragment.Companion.textArg
import com.example.and2.homework.and.homework.s01.model.Post
import com.example.and2.homework.and.homework.s01.util.formatNumberShortPrecise
import com.example.and2.homework.and.homework.s01.viewmodel.PostViewModel

class DetailsPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    private lateinit var binding: FragmentDetailsPostBinding

    private val onInteractionListener = object : OnInteractionListener {
        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            findNavController().navigate(
                R.id.action_detailsPostFragment_to_editPostFragment,
                Bundle().apply {
                    textArg = post.content
                })
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
            findNavController().navigateUp()
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

        override fun onPostClick(it: Post) {
            findNavController().navigate(
                R.id.action_feedFragment_to_detailsPostFragment, Bundle().apply {
                    post = it
                })
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsPostBinding.inflate(layoutInflater, container, false)
        val post = arguments?.post
        post?.let {
            binding.apply {
                author.text = post.author
                published.text = post.published
                like.isChecked = post.likedByMe
                like.text = formatNumberShortPrecise(post.likes)
                share.text = formatNumberShortPrecise(post.shares)
                content.text = post.content
                if (post.videoUrl != null) {
                    videoPic.visibility = View.VISIBLE
                    playButton.visibility = View.VISIBLE
                    playButton.setOnClickListener {
                        onInteractionListener.onVideoClick(post)
                    }
                } else {
                    videoPic.visibility = View.GONE
                    playButton.visibility = View.GONE
                }
                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    onInteractionListener.onRemove(post)
                                    true
                                }

                                R.id.edit -> {
                                    onInteractionListener.onEdit(post)
                                    true
                                }

                                else -> false
                            }
                        }
                    }.show()
                }
                like.setOnClickListener {
                    onInteractionListener.onLike(post)
                }
                share.setOnClickListener {
                    onInteractionListener.onShare(post)
                }
            }
        }
        return binding.root
    }

    companion object {
        private const val KEY_POST = "post"
        var Bundle.post: Post?
            set(value) = putParcelable(KEY_POST, value)
            get() = getParcelable(KEY_POST)
    }
}