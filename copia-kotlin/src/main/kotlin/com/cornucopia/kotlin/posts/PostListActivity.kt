package com.cornucopia.kotlin.posts

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.databinding.ActivityPostListBinding
import org.jetbrains.anko.contentView

class PostListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_list)
        val binding = ActivityPostListBinding.bind(contentView!!)

        binding.rvPosts.layoutManager =
            LinearLayoutManager(this)

        var postRepository = PostRepository(PostNetworkDataSource())

        var modelView = ViewModelProviders.of(this, PostModelViewFactory(postRepository))
                .get(PostViewModel::class.java)

        val adapter = PostListAdapter()

        modelView.posts.observe(this, Observer<PagedList<Post>> {
            posts ->
            if (null != posts) {
                Log.e("thom", "size: " + posts.size)
                adapter.submitList(posts)
            }
        })

        binding.rvPosts.adapter = adapter
    }
}