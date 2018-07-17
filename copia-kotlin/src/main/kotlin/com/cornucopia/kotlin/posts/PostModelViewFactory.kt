package com.cornucopia.kotlin.posts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class PostModelViewFactory(repository: PostRepository) : ViewModelProvider.NewInstanceFactory() {

    val rep = repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(rep) as T
    }
}