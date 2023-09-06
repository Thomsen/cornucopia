package com.cornucopia.kotlin.perf

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import com.cornucopia.kotlin.R

//
// Created by Thomsen on 09/06/2022.
//

class AnrActivity: Activity() {

    // profiler analytics

    // __libc_init() -> main() -> start() -> CallStaticVoidMethod() -> Invoke() -> ActivityThread main() -> loop() ->
    // dispatchMessage() -> handleCallback() -> run() -> Choreographer doFrame() -> ViewRootImpl doTraversal() ->
    // Activity performCreate()

    // performCreate()
    // onCreate()
    // sleep()
    // sleep()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perf_anr)

        SystemClock.sleep(10 * 1000)
    }
}