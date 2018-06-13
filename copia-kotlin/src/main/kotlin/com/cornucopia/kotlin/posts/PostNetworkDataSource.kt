package com.cornucopia.kotlin.posts

import android.app.usage.NetworkStats
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.DataSource
import android.arch.paging.ItemKeyedDataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.net.NetworkInfo
import androidx.net.toUri
import com.cornucopia.kotlin.weather.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.MulticastSocket
import java.net.URL
import java.util.*
import java.util.concurrent.Executors

val URL_POSTS : String = "http://jsonplaceholder.typicode.com/posts"

class PostNetworkDataSource {

    var liveData = MutableLiveData<PagedList<Post>>()

    fun getPosts() : LiveData<PagedList<Post>> {

        var executors = Executors.newFixedThreadPool(3)

        val dataSource = SourceFactory()

//        val pagedListConfig = PagedList.Config.Builder()
//                .setEnablePlaceholders(false)
//                .setInitialLoadSizeHint(10 * 2)
//                .setPageSize(10)
//                .build()

        // No direct method <init>(Ljava/util/concurrent/Executor;)V in class
        // Landroid/arch/lifecycle/ComputableLiveData  -
        var pageList = LivePagedListBuilder(dataSource, 100)
                .setFetchExecutor(executors)
                .build()

//        val refreshState = Transformations.switchMap(dataSource.sourceLiveData) {
//            it.initialLoad
//        }

        return pageList
    }

    class SourceFactory() : DataSource.Factory<String, Post> () {

//        val sourceLiveData = MutableLiveData<PostItemKeyedDataSource>()
        override fun create(): DataSource<String, Post> {
            val source = PostItemKeyedDataSource()
//            sourceLiveData.postValue(source)
            return source
        }

    }

    class PostItemKeyedDataSource() : ItemKeyedDataSource<String, Post>() {

//        val initialLoad = MutableLiveData<NetworkState>()

        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<Post>) {
            var uri = URL_POSTS.toUri().buildUpon()
                    .appendQueryParameter("_limit", "100")
                    .appendQueryParameter("_start", "0")
                    .build()

            val jsonPost = NetworkUtils.getResponseFromHttpUrl(URL(uri.toString()))

            var gson = Gson()
            var typeOf = genericType<List<Post>>()
            // java.util.ArrayList cannot be cast to android.arch.paging.PagedList
            var response = gson.fromJson<List<Post>>(jsonPost, typeOf)

            callback.onResult(response)
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Post>) {
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Post>) {

        }

        override fun getKey(item: Post): String = item.title

        inline fun <reified T> genericType() = object : TypeToken<T>(){}.type
    }

//    inline fun <reified T> genericType() = object : TypeToken<T>(){}.type
}
