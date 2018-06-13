package com.cornucopia.kotlin.posts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import androidx.net.toUri
import com.cornucopia.kotlin.weather.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.MulticastSocket
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

class PostNetworkDataSource {

    val URL_POSTS : String = "http://jsonplaceholder.typicode.com/posts"

    var liveData = MutableLiveData<List<Post>>()

    fun getPosts() : LiveData<List<Post>> {

        Executors.newFixedThreadPool(3).execute({
            var uri = URL_POSTS.toUri().buildUpon()
                    .appendQueryParameter("_limit", "100")
                    .appendQueryParameter("_start", "0")
                    .build()

            val jsonPost = NetworkUtils.getResponseFromHttpUrl(URL(uri.toString()))

            var gson = Gson()
            var typeOf = genericType<List<Post>>()
            var response = gson.fromJson<List<Post>>(jsonPost, typeOf)


            liveData.postValue(response)
        })

        return liveData;
    }

    inline fun <reified T> genericType() = object : TypeToken<T>(){}.type
}
