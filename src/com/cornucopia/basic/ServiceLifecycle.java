package com.cornucopia.basic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceLifecycle extends Service {
	
	public final static String TAG_SERVICE_LIFECYCLE = "tag_service_lifecycle";

	/*
	 *  onBind是必须重载Service的方法
	 */
	@Override
	public IBinder onBind(Intent arg0) {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onBind run...");
		
		return null;
	}
	
	@Override
	public void onRebind(Intent intent) {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onRebind run...");
		
		super.onRebind(intent);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onUnbind run...");
		
		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onCreate run...");
		
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onStartCommand run...");
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onStart run...");
		
		super.onStart(intent, startId);
		
	}

	@Override
	public void onDestroy() {
		
		Log.i(TAG_SERVICE_LIFECYCLE, "onDestory run...");
		
		super.onDestroy();
	}

}
