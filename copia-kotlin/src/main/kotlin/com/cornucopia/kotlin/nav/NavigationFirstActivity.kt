package com.cornucopia.kotlin.nav

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cornucopia.kotlin.R
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_navigation_first.*

class NavigationFirstActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation_first);

        RxView.clicks(btn_nav_second)
                .subscribe(mToSecondConsumer, mErrorConsumer);
    }

    val mToSecondConsumer = Consumer<Any>() {
        var intent = Intent(this, NavigationSecondActivity::class.java)
        startActivity(intent);
    }

    val mErrorConsumer = Consumer<Throwable>() {

    }
}



