package com.example.and2.homework.and.homework.s01.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.databinding.CardPostBinding
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.util.formatNumberShortPrecise

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) :
    ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
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
                        onInteractionListener.onClick(post)
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
    }
}

