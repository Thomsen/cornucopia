package com.cornucopia.lifecycle;
//
// Created by Thomsen on 30/03/2022.
//

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cornucopia.R;
import com.cornucopia.lifecycle.launch.LaunchInstanceActivity;
import com.cornucopia.lifecycle.launch.LaunchResultActivity;
import com.cornucopia.lifecycle.launch.LaunchStandardActivity;
import com.cornucopia.lifecycle.launch.LaunchTaskActivity;
import com.cornucopia.lifecycle.launch.LaunchTopActivity;

public class LaunchModeActivity extends Activity {


    public String TAG_ACTIVITY_LIFECYCLE = "lifecycle_launch_mode";


    @Override
    public void onContentChanged() {
        super.onContentChanged();

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity content changed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch_mode);


        setupListener();

        // The activity is being created.

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity create");
    }

    private void setupListener() {
        Context self = this;
        findViewById(R.id.launch_standard).setOnClickListener(v -> {
            Intent intent = new Intent(self, LaunchStandardActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.launch_top).setOnClickListener(v -> {
            Intent intent = new Intent(self, LaunchTopActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.launch_task).setOnClickListener(v -> {
            Intent intent = new Intent(self, LaunchTaskActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.launch_instance).setOnClickListener(v -> {
            Intent intent = new Intent(self, LaunchInstanceActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.launch_result).setOnClickListener(v -> {
            Intent intent = new Intent(self, LaunchResultActivity.class);
            startActivityForResult(intent , 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity on result");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity restart");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // The activity is about to become visible.

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity start");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity new intent");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity restore instance state");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity post create");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // The activity has become visible (it is now "resumed").

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity resume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity post resume");
    }



    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity attached to window");
        Log.i(TAG_ACTIVITY_LIFECYCLE, "--------------------------------");

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Another activity is taking focus (this activity is about to be "paused").

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity pause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity save instance state");
    }

    @Override
    protected void onStop() {
        super.onStop();

        // The activity is no longer visible (it is now "stopped")

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // The activity is about to be destroyed.

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity destroy");
        Log.i(TAG_ACTIVITY_LIFECYCLE, "--------------------------------");
    }


    /*
     *  横屏切换
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(TAG_ACTIVITY_LIFECYCLE, "activity configuration changed");

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG_ACTIVITY_LIFECYCLE, "orientation landcape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d(TAG_ACTIVITY_LIFECYCLE, "orientation portrait");
        }
    }

}