package com.cornucopia.kotlin.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
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

        modelView.posts.observe(this, Observer {
            posts ->
            if (null != posts) {
                Log.e("thom", "size: " + posts.size)
            }
        })

    }
}