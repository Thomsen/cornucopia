package com.cornucopia.kotlin.posts

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cornucopia.kotlin.R

class PostListAdapter() : PagedListAdapter<Post, PostListAdapter.PostHolderView>(POST_COMPARTOR) {

    override fun getItemCount(): Int {
        var count = super.getItemCount() + 0
        return count
    }

    override fun onBindViewHolder(holder: PostHolderView, position: Int, payloads: MutableList<Any>) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolderView {
        return PostHolderView.create(parent)
    }

    override fun onBindViewHolder(holder: PostHolderView, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val POST_COMPARTOR = object : DiffUtil.ItemCallback<Post> () {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem.title.equals( newItem.title)

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                    oldItem == newItem
        }
    }


    class PostHolderView(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val body: TextView = itemView.findViewById(R.id.tv_post_body)

        private var post: Post? = null

        fun bind(post: Post?) {
            this.post = post
            body.text = post?.body ?: "body loading"
        }

        companion object {
            fun create(parent: ViewGroup) : PostHolderView {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_post_item, parent, false)
                return PostHolderView(view)
            }
        }
    }
}