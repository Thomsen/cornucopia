package com.cornucopia.basic;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadcastReceiverLifecycle extends BroadcastReceiver {

	public final static String TAG_RECEIVER_LIFECYCLE = "tag_receiver_lifecycle";
	
	public BroadcastReceiverLifecycle() {
		Log.i(TAG_RECEIVER_LIFECYCLE, "time: " + Calendar.getInstance().getTime().toLocaleString());   
        Log.i(TAG_RECEIVER_LIFECYCLE , "pid: " + String.valueOf(android.os.Process.myPid()));   
        Log.i(TAG_RECEIVER_LIFECYCLE , "tid: " + String.valueOf(android.os.Process.myTid()));   
	}
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.i(TAG_RECEIVER_LIFECYCLE, "onReceiver run...");
		Toast.makeText(arg0, "Alarm Receiver", Toast.LENGTH_SHORT).show();
	}

}
