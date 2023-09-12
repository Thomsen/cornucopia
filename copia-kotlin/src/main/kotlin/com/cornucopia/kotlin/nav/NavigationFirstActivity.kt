package com.cornucopia.kotlin.nav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornucopia.kotlin.R
import com.cornucopia.kotlin.databinding.ActivityNavigationFirstBinding
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class NavigationFirstActivity : AppCompatActivity() {

    lateinit var binding: ActivityNavigationFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationFirstBinding.inflate(layoutInflater)
        setContentView(binding.root);

        RxView.clicks(binding.btnNavSecond)
            .subscribe(mToSecondConsumer, mErrorConsumer);
    }

    val mToSecondConsumer = Consumer<Any>() {
        var intent = Intent(this, NavigationSecondActivity::class.java)
        startActivity(intent);
    }

    val mErrorConsumer = Consumer<Throwable>() {

    }
}



