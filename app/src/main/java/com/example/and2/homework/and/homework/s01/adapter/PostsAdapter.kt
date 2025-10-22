package com.example.and2.homework.and.homework.s01.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.and2.homework.and.homework.s01.R
import com.example.and2.homework.and.homework.s01.databinding.CardPostBinding
import com.example.and2.homework.and.homework.s01.dto.Post
import com.example.and2.homework.and.homework.s01.util.formatNumberShortPrecise

typealias OnLikeListener = (Post) -> Unit
typealias OnSharesListener = (Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onSharesListener: OnSharesListener
) :
    ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(context = parent.context, binding, onLikeListener, onSharesListener)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        private val context: Context,
        private val binding: CardPostBinding,
        private val onLikeListener: OnLikeListener,
        private val onSharesListener: OnSharesListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                author.text = context.getString(post.authorId)
                published.text = post.published
                content.text = context.getString(post.contentId)
                likeCount.text = formatNumberShortPrecise(post.likes)
                shareCount.text = formatNumberShortPrecise(post.shares)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like
                )
                like.setOnClickListener {
                    onLikeListener(post)
                }
                share.setOnClickListener {
                    onSharesListener(post)
                }
            }
        }
    }
}

