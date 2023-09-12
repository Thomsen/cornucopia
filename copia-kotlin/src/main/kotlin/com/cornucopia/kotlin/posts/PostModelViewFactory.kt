package com.cornucopia.kotlin.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PostModelViewFactory(repository: PostRepository) : ViewModelProvider.NewInstanceFactory() {

    val rep = repository

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostViewModel(rep) as T
    }
}