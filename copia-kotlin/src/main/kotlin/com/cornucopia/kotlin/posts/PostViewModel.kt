package com.cornucopia.kotlin.posts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

class PostViewModel(repository: PostRepository) : ViewModel() {

    var posts : LiveData<List<Post>> = repository.getPosts()

}
