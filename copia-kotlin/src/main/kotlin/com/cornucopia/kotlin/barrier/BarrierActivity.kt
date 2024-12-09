package com.cornucopia.kotlin.barrier

import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cornucopia.kotlin.databinding.ActivityBarrierBinding
import java.lang.Thread.sleep
import kotlin.concurrent.thread


//
// Created by Thomsen on 19/04/2024.
//
class BarrierActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBarrierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thread {
            sleep(3000)  // crash
//            binding.image.setImageResource(R.drawable.ic_launcher_background)
//            binding.text.text = "thread text"
//            binding.text.setBackgroundColor(Color.BLACK)
            binding.text.setTextColor(Color.RED)
            sendBarrier()
        }

        binding.image.setOnClickListener {
            Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendBarrier() {
//        val enqueueSyncBarrier = MessageQueue::class.java.getDeclaredMethod(
//            "enqueueSyncBarrier", Long.javaClass)
//        enqueueSyncBarrier.isAccessible = true
//
//        val token = enqueueSyncBarrier.invoke(Looper.getMainLooper().queue,
//            SystemClock.uptimeMillis())
//        Log.d("BarrierActivity", "barrier token: $token")


        val postSyncBarrier = MessageQueue::class.java.getDeclaredMethod(
            "postSyncBarrier")
        postSyncBarrier.isAccessible = true
//
        val token = postSyncBarrier.invoke(Looper.getMainLooper().queue)

        Log.d("BarrierActivity", "barrier token: $token")

    }

}