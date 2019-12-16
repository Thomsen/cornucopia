package com.cornucopia.kotlin.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.cornucopia.kotlin.R
import kotlinx.android.synthetic.main.activity_post_list.*

class PostListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_list)

        rv_posts.layoutManager = LinearLayoutManager(this)

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

        rv_posts.adapter = adapter
    }
}