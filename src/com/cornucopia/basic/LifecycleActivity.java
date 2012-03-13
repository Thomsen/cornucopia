package com.cornucopia.basic;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class LifecycleActivity extends Activity implements
		OnGlobalLayoutListener, OnClickListener {

	public final static String TAG_SERVICE_VIEWWITDH = "tag_service_viewwitdh";

	Button startService;
	Button stopService;
	Button bindService;
	Button unBindService;

	Button startBroadcast;
	Button stopBroadcast;

	LinearLayout linearLayout;

	Intent intent;

	ServiceLifecycle mService;
	boolean isBound = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		linearLayout = new LinearLayout(this);
		linearLayout.setLayoutParams(params);

		// 若不设置排列方向，则只能显示出startService
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		startService = new Button(this);
		startService.setLayoutParams(params);
		startService.setId(2301);
		startService.setText("Start Service");
		startService.setOnClickListener(this);

		linearLayout.addView(startService);

		stopService = new Button(this);
		stopService.setLayoutParams(params);
		stopService.setId(2302);
		stopService.setText("Stop Service");
		stopService.setOnClickListener(this);

		linearLayout.addView(stopService);

		bindService = new Button(this);
		bindService.setLayoutParams(params);
		bindService.setId(2303);
		bindService.setText("Bind Service");
		bindService.setOnClickListener(this);

		linearLayout.addView(bindService);

		unBindService = new Button(this);
		unBindService.setLayoutParams(params);
		unBindService.setId(2304);
		unBindService.setText("Un Bind Service");
		unBindService.setOnClickListener(this);

		linearLayout.addView(unBindService);

		startBroadcast = new Button(this);
		startBroadcast.setLayoutParams(params);
		startBroadcast.setId(2305);
		startBroadcast.setText("Start Broadcast");
		startBroadcast.setOnClickListener(this);

		linearLayout.addView(startBroadcast);

		stopBroadcast = new Button(this);
		stopBroadcast.setLayoutParams(params);
		stopBroadcast.setId(2306);
		stopBroadcast.setText("Stop Broadcast");
		stopBroadcast.setOnClickListener(this);

		linearLayout.addView(stopBroadcast);

		setContentView(linearLayout);

		Log.i(TAG_SERVICE_VIEWWITDH, "getWidth " + linearLayout.getWidth());
		Log.i(TAG_SERVICE_VIEWWITDH, "getHeight " + linearLayout.getHeight());
		Log.i(TAG_SERVICE_VIEWWITDH,
				"getMeasuredWidth " + linearLayout.getMeasuredWidth());
		Log.i(TAG_SERVICE_VIEWWITDH,
				"getMeasuredHeight " + linearLayout.getMeasuredHeight());

		// 获取view的width、height，需要实现接口
		linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@Override
	public void onGlobalLayout() {

		Log.i(TAG_SERVICE_VIEWWITDH, "getWidth " + linearLayout.getWidth());
		Log.i(TAG_SERVICE_VIEWWITDH, "getHeight " + linearLayout.getHeight());
		Log.i(TAG_SERVICE_VIEWWITDH,
				"getMeasuredWidth " + linearLayout.getMeasuredWidth());
		Log.i(TAG_SERVICE_VIEWWITDH,
				"getMeasuredHeight " + linearLayout.getMeasuredHeight());
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case 2301: {

			startService(intent);
			Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
			break;

			// 需要在Manifest中注册，而service全为小写
		}
		case 2302: {
			stopService(intent);
			Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
			break;
		}
		case 2303: {
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			break;
		}
		case 2304: {
			unbindService(mConnection);
			break;
		}
		case 2305: {

			Log.i(BroadcastReceiverLifecycle.TAG_RECEIVER_LIFECYCLE,
					"current pid: "
							+ String.valueOf(android.os.Process.myPid()));
			Log.i(BroadcastReceiverLifecycle.TAG_RECEIVER_LIFECYCLE,
					"current tid: "
							+ String.valueOf(android.os.Process.myTid()));

			((AlarmManager) this.getSystemService(Context.ALARM_SERVICE))
					.setRepeating(AlarmManager.RTC_WAKEUP, System
							.currentTimeMillis(), 5 * 1000, PendingIntent
							.getBroadcast(this, 0, new Intent(this,
									BroadcastReceiverLifecycle.class), 0));
			break;
		}
		case 2306: {
			
			Log.i(BroadcastReceiverLifecycle.TAG_RECEIVER_LIFECYCLE, "Cancel Broadcast");
			
			((AlarmManager) this.getSystemService(Context.ALARM_SERVICE))
					.cancel(PendingIntent.getBroadcast(this, 0, new Intent(
							this, BroadcastReceiverLifecycle.class), 0));
			break;
		}
		default:
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		intent = new Intent(LifecycleActivity.this, ServiceLifecycle.class);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	// Bind Service需要ServiceConnection
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			mService = new ServiceLifecycle();
			isBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mService = null;
			isBound = false;
		}

	};

}
