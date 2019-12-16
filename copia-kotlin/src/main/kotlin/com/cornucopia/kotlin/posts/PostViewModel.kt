package com.cornucopia.kotlin.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList


class PostViewModel(repository: PostRepository) : ViewModel() {

    var posts : LiveData<PagedList<Post>> = repository.getPosts()

}
