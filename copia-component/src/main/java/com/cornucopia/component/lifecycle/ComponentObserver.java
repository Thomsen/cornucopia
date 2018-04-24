package com.cornucopia.component.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by thom on 31/1/2018.
 */

public class ComponentObserver implements LifecycleObserver {

    private static final String TAG = "LifecycleObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void createActivity() {
        Log.i(TAG, "create activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void startActivity() {
        Log.i(TAG, "start activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stopActivity() {
        Log.i(TAG, "stop activity");
    }

    
}
