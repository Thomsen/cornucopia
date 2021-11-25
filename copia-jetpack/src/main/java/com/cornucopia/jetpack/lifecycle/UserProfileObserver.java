package com.cornucopia.jetpack.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by thom on 31/1/2018.
 */

public class UserProfileObserver implements LifecycleObserver {

    private static final String TAG = "LifecycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        Log.i(TAG, "create fragment");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        Log.i(TAG, "start fragmment");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        Log.i(TAG, "stop fragment");
    }

    
}
