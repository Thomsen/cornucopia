package com.cornucopia.devices.timer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class TestOnStartCommandService extends Service {
	
	private static final String TAG = "TestOnStartCommandService";
	
	private Timer timer;
	
	private TimerTask timerTask;
	
	protected static final int HANDLE_ONE = 0;
	
	private int timerCount = 0;
	
	public static final String TIMER_RESTART = "timer_restart";
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLE_ONE: {
				Log.i(TAG, "count: " + msg.arg1);
				Toast.makeText(getApplicationContext(), "count: " + msg.arg1, Toast.LENGTH_SHORT).show();
				appendContentToAnalysis("service: " + msg.arg1, "alarm_Performance.txt");
				break;
			}
			default:
				break;
			}
		}
	};
	
	public void onCreate() {
		super.onCreate();
	}
	
	public void onDestroy() {
//		super.onDestroy();
		Log.i(TAG, "service on destroy");
		
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isTimerRestart = intent.getBooleanExtra(TIMER_RESTART, false);
		if (isTimerRestart) {
			appendContentToAnalysis("service: stop timer", "alarm_Performance.txt");
			stopTimer();
		}
		startTimer();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void startTimer() {
		if (timer == null) {
        	timer = new Timer();
        	if (timerTask == null) {
        		timerTask = new TimerTask() {

					@Override
					public void run() {
						Message msg = new Message();
						msg.what = HANDLE_ONE;
						msg.arg1 = timerCount;
						mHandler.sendMessage(msg);
						timerCount++;
					}
        			
        		};
        	}
        	timer.schedule(timerTask, 0, 5000);
        }
	}
	
	private void stopTimer() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	
	public static void appendContentToAnalysis(String content, String fileName) {
		// 判断sdcard

		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
	
		String strDate = format.format(new Date());
		
		File file = new File(Environment.getExternalStorageDirectory(), fileName);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		
		FileWriter fileWriter = null;
		try {
//			fileWriter = new FileWriter(fileName, true); // 默认在/file.txt，导致erofs错误
			fileWriter = new FileWriter(file.getAbsolutePath(), true);

//			fileWriter.append(content);
			fileWriter.write(strDate + "\t" + content + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
