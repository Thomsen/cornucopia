package com.cornucopia.kotlin.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.widget.Toast

class LocalService : Service(), ILocalService{

    override fun getName() : String {
        return "LocalService";
    }

    override fun isBound(): Boolean {
        return isBound
    }

    override fun sendMessage(msg: String) {
        if (null == binder) {
            var message: Message = mHandler.obtainMessage()
            message.what = 1
            message.obj = msg
            message.sendToTarget()
        } else {
            binder!!.sendMessage(msg)
        }
    }

    var mHandler: Handler = object: Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (1 == msg!!.what) {
                Toast.makeText(mContext, "local handler: " + msg!!.obj, Toast.LENGTH_LONG).show()
            }
        }
    }



    private var isBound = false

    private var binder: LocalBinder? = null

    private lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext = this
        Toast.makeText(this, "local service created", Toast.LENGTH_SHORT).show();
    }

    override fun onBind(intent: Intent?): IBinder? {
        isBound = true;
        binder = LocalBinder()
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        isBound = false
        binder = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class LocalBinder : Binder() {
        fun getService(): LocalService = this@LocalService

        fun sendMessage(msg: String) {
            var message: Message = mHandler.obtainMessage()
            message.what = 1
            message.obj = msg
            message.sendToTarget()
        }

        var mHandler: Handler = object: Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (1 == msg!!.what) {
                    Toast.makeText(mContext, "binder local handler: " + msg!!.obj,
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}