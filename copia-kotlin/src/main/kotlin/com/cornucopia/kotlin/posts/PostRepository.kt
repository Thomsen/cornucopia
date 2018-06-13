package com.cornucopia.kotlin.posts

import android.arch.lifecycle.LiveData

class PostRepository(networkDataSource: PostNetworkDataSource) {

    var dataSource = networkDataSource

    fun getPosts() : LiveData<List<Post>> {
        return dataSource.getPosts()
    }
}
