package com.cornucopia.devices.timer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cornucopia.R;
import com.cornucopia.application.basic.CorncopiaActivity;
import com.cornucopia.devices.PhoneCallReceiver;

public class TimerTaskActivity extends CorncopiaActivity {
	
	private static TextView tvTimerCount;
	private static TextView tvAlarmCount;
	private static TextView tvAlarmUpCount;
	
	protected static final int HANDLE_ONE = 0;
	
	protected static final int VALIDATE_NEWWORK = 1;
	
	private Timer timer = null;
	
	private TimerTask timerTask = null;
	
	private TimerTask networkValidateTimerTask;
	
	private int timerCount = 0;
	
	private static int alarmCount = 0;
	
	private static int alarmUpCount = 0;
	
	AlarmManager alarmManager;
	PendingIntent pendingIntent;
	PendingIntent pendingIntent1;
	PendingIntent pendingIntent2;
	
	private Context mContext;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLE_ONE: {
				tvTimerCount.setText("timer: " + msg.arg1);
				TestOnStartCommandService.appendContentToAnalysis("timer: " + msg.arg1, "alarm_Performance.txt");
				break;
			}
			case VALIDATE_NEWWORK: {
				ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    Toast.makeText(mContext, "当前网络连接不可用，请检查网络环境", Toast.LENGTH_SHORT).show();
//                    noticeNetworkBreak();
                    if (PhoneCallReceiver.isPhoneState) {
                    	Log.i("thom", "phone calling");
                    } else {
                    	Log.i("thom", "phone called");
                    }
                    
                } else {
                    if (!networkInfo.isConnected()) {
                    	Toast.makeText(mContext, "当前网络连接不可用，请启用网络", Toast.LENGTH_SHORT).show();
                        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                            connManager.startUsingNetworkFeature(networkInfo.getType(), "wap"); // OPhone
                        }
                    }
                }
				break;
			}
			default:
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_timer_task);
		mContext = this;
		
        tvAlarmCount = (TextView) findViewById(R.id.tv_alarm_count);
        tvTimerCount = (TextView) findViewById(R.id.tv_timer_count);
        tvAlarmUpCount = (TextView) findViewById(R.id.tv_alarmup_count);
        
		timerCount();

		// 启动服务
		Intent intent = new Intent(this, TestOnStartCommandService.class);
		startService(intent);

		initAlarmTimerReiver();
	}
	
	protected void onStart() {
		super.onStart();
		
    	alarmCount();
    	alarmUpCount();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		
    	if (timerTask != null) {
    		timerTask.cancel();
    		timerTask = null;
    	}
    	if (timer != null) {
    		timer.cancel();
    		timer = null;
    	}
    	if (alarmManager != null) {
    		alarmManager.cancel(pendingIntent1);
    		alarmManager.cancel(pendingIntent2);
    		alarmManager.cancel(pendingIntent);
    		alarmManager = null;
    	}
    	
    	// 由于pendingIntent在中间重新赋值了，导致第一个pendingIntent常驻内存
    	
    	alarmCount = 0;
    	alarmUpCount = 0;
		
		Intent intent = new Intent(this, TestOnStartCommandService.class);
        stopService(intent);
	}
	
	private void timerCount() {
        if (timer == null) {
        	timer = new Timer();
        	if (timerTask == null) {
        		timerTask = new TimerTask() {

					@Override
					public void run() {
						Message msg = new Message();
						msg.what = HANDLE_ONE;
						msg.arg1 = timerCount;
//						mHandler.sendMessage(msg);
						mHandler.sendMessage(msg);
						timerCount++;
					}
        			
        		};
        	}
        	timer.schedule(timerTask, 0, 5000);
        }
	}
	
	private void initAlarmTimerReiver() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		
		Intent i = new Intent();
		i.setAction(AlarmTimerBroadcast.RECEIVER_TIMER_RESTART);
		i.setClass(this, AlarmTimerBroadcast.class);
		pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
		long startTime = SystemClock.elapsedRealtime();
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTime, 60*60*1000, pendingIntent);
//		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTime, 10*1000, pendingIntent);
	}
	
	private void alarmCount() {
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//		registerReceiver(myReceiver, filter);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent();
		i.setAction("com.example.alarm");
//		i.setClassName(this, "com.example.lockscreenaffect.PhoneCallReceiver");
		i.setClass(this, MyReceiver.class);
		pendingIntent1 = PendingIntent.getBroadcast(this, 0, i, 0);
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(System.currentTimeMillis());
//		cal.add(Calendar.SECOND, 0);   // in order to RTC flag
		long startTime = SystemClock.elapsedRealtime(); // in order to ELAPSED
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, startTime, 5000, pendingIntent1);
	}

	
	private void alarmUpCount() {
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent();
		i.setClass(this, MyReceiver.class);
		i.setAction("com.example.alarmwakeup");
		pendingIntent2 = PendingIntent.getBroadcast(this, 0, i, 0);
		long startTime = SystemClock.elapsedRealtime();
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, startTime, 5000, pendingIntent2);
	}
	
	// 在Manifest中静态注册，需要设置访问权限public，同样需要static
	public static class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.i("thom", "action: " + action);
			if ("com.example.alarm".equals(action)) {
				if (tvAlarmCount != null) {
					tvAlarmCount.setText("alarm: " + alarmCount);
					TestOnStartCommandService.appendContentToAnalysis("alarm: " + alarmCount, "alarm_Performance.txt");
					alarmCount++;
				}
				
			} else if ("com.example.alarmwakeup".equals(action)) {
				if (tvAlarmUpCount != null) {
					tvAlarmUpCount.setText("wakeup: " + alarmUpCount);
					TestOnStartCommandService.appendContentToAnalysis("wakeup: " + alarmUpCount, "alarm_Performance.txt");
					alarmUpCount++;
				}
				
			}
			
		}
		
	}
	
	private void suRun() {
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec("su");
			
			// TODO 
			Log.i("thom", "su run");
			
			proc.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			proc.destroy();
		}
		
	}
	
	private void startTestNetwork() {
        //
        // 检查网络是否连接
        //
		if (timer == null) {
			timer = new Timer();
        }
		networkValidateTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(VALIDATE_NEWWORK);
            }
        };
        timer.schedule(this.networkValidateTimerTask, 0, 20000);
	}
	
    /*
     * 当back时，应用退出，但是timertask并没有被停止，
     * 这样重新运行应用后，就会有两个timertask在运行。
     * android.os.Process.killProcess(android.os.Process.myPid()); // 会停止timer
     */
}
