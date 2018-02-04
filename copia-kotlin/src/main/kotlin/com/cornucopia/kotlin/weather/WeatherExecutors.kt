package com.cornucopia.kotlin.weather

import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by thom on 3/2/2018.
 */
class WeatherExecutors {

    var diskIO: Executor? = null;

    var networkIO: Executor? = null;

    var mainThread: Executor? = null;


    private constructor(diskIO: Executor, networkIO: Executor, mainThread: Executor) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    companion object {

        val INSTANCE by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WeatherExecutors(Executors.newSingleThreadExecutor(),
                    Executors.newFixedThreadPool(3),
                    MainThreadExecutor());
        }

    }

    class MainThreadExecutor : Executor {

        private val mainThreadHandler = android.os.Handler(Looper.getMainLooper());

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command);
        }

    }

}