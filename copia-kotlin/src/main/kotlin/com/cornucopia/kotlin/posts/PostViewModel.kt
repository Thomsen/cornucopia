package com.cornucopia.kotlin.posts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList

class PostViewModel(repository: PostRepository) : ViewModel() {

    var posts : LiveData<PagedList<Post>> = repository.getPosts()

}
