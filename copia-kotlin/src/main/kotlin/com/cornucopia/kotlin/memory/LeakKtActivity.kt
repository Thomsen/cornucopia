package com.cornucopia.kotlin.memory

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import com.cornucopia.kotlin.R
import kotlinx.android.synthetic.main.activity_kt_leak.*

class LeakKtActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kt_leak)

        btn_kt_leak.setOnClickListener{startAsyncWork()}
    }

    private fun startAsyncWork() {
        val work = Runnable { SystemClock.sleep(20000)}
        Thread(work).start()
    }
}