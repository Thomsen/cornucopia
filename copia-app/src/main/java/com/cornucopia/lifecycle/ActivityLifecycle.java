package com.cornucopia.lifecycle;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import com.cornucopia.R;

public class ActivityLifecycle extends Activity {

	// 07-25 06:17:45.483 3676-3695/system_process I/ActivityManager: Displayed com.cornucopia/.lifecycle.ActivityLifecycle: +365ms

	public final static String TAG_ACTIVITY_LIFECYCLE = "lifecycle";

	private long mStartTime;

	private long mEndTime;

	@Override
	public void onContentChanged() {
		mStartTime = System.nanoTime();
		super.onContentChanged();
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "activity content changed");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		LinearLayout linearLayout01 = new LinearLayout(this);
//		TextView textView = new TextView(this);
//		textView.setText("Activity Lifecycle");
//		linearLayout01.addView(textView);
//		setContentView(linearLayout01);

		setContentView(R.layout.activity_fragment_lifecycle);

		TextView textView = (TextView) findViewById(R.id.tv_lifecycle);
		textView.setText("Activity Lifecycle");

		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.frame_lifecycle, new FragmentLifecycle());
		fragmentTransaction.commit();

		
		// The activity is being created.
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "activity create");
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

		Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
			// IdleHandler 会在Looper的消息队列处理完当前阻塞（等待下一个消息）被调用
			@Override
			public boolean queueIdle() {
				// ams and wms
				// wm.addView
				// view measure/layout/draw finish

				Log.i(TAG_ACTIVITY_LIFECYCLE, "activity resume idle handler");

				Log.i(TAG_ACTIVITY_LIFECYCLE, "Displayed ActivityLifecycle onCreate to Idle: "
						+ (System.nanoTime() - mStartTime));

				return false;
			}
		});

		mEndTime = System.nanoTime();

		Log.i(TAG_ACTIVITY_LIFECYCLE, "Displayed ActivityLifecycle onCreate to onResume: "
				+ (mEndTime - mStartTime));  // 2800806 = 2.8 ms
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		Log.i(TAG_ACTIVITY_LIFECYCLE, "activity attached to window");
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		
		Log.i(TAG_ACTIVITY_LIFECYCLE, "activity post resume");
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
