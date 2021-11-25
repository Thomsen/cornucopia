package com.cornucopia.kotlin.posts

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

class PostRepository(networkDataSource: PostNetworkDataSource) {

    var dataSource = networkDataSource

    fun getPosts() : LiveData<PagedList<Post>> {
        return dataSource.getPosts()
    }
}
