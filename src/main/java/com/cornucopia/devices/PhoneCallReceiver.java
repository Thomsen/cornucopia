package com.cornucopia.devices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneCallReceiver extends BroadcastReceiver {
	
	public static boolean isPhoneState = false;
	
	private Context mContext;
	
	@Override
	public void onReceive(Context context, Intent intent) {

		mContext = context;
		
		String action = intent.getAction();
		
		Log.i("thom", "action" + action);
		
		if (Intent.ACTION_NEW_OUTGOING_CALL.equals(action)) {
			
		} else {
			
		}
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		
	}
	
	
	
	PhoneStateListener listener = new PhoneStateListener() {
		
		boolean isIdle = false;
		boolean isOffhook = false;
		boolean isRinging = false;
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
		
			switch(state) {
				case TelephonyManager.CALL_STATE_IDLE: {
					Log.i("thom", "idle");
					
//					if (isOffhook) {
//						MainActivity.isPhoneState = false;
//					}
					
					isPhoneState = false;
					
					break;
				}
				case TelephonyManager.CALL_STATE_OFFHOOK: {
					Log.i("thom", "offhook");
					isOffhook = true;
					
					isPhoneState = true;
//					if (isRinging) {
//						MainActivity.isPhoneState = true;
//					}
					
					break;
				}
				case TelephonyManager.CALL_STATE_RINGING: {
					Log.i("thom", "ringing");
					
					isRinging = true;
					
					isPhoneState = true;
					
					break;
				}
				default:
					break;
			}
		}
		
	};

}
