package com.cornucopia.kotlin.posts

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

class PostRepository(networkDataSource: PostNetworkDataSource) {

    var dataSource = networkDataSource

    fun getPosts() : LiveData<PagedList<Post>> {
        return dataSource.getPosts()
    }
}
