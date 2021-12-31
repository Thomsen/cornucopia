package com.cornucopia.kotlin.ml

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import com.cornucopia.kotlin.databinding.ActivityLeakBinding

class KLeakActivity: Activity() {

    private lateinit var binding: ActivityLeakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLeak.setOnClickListener {
            startAsyncWork()
        }
    }

    private fun startAsyncWork() {
        val work = Runnable { SystemClock.sleep(20000) }
        Thread(work).start()
    }
}