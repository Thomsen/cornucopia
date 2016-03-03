package com.cornucopia.devices.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmTimerBroadcast extends BroadcastReceiver {
	
	public static final String RECEIVER_TIMER_RESTART = "timer_restart";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		
		if (RECEIVER_TIMER_RESTART.equals(action)) {
			Intent i = new Intent(context, TestOnStartCommandService.class);
			i.putExtra(TestOnStartCommandService.TIMER_RESTART, true);
			context.startService(i);
			
		}

	}

}
