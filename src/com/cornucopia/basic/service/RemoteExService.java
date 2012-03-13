package com.cornucopia.basic.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.widget.Toast;

public class RemoteExService extends Service {

	@Override
	public void onCreate() {
		super.onCreate();
		
		Toast.makeText(this, "服务启动", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 不同的接口返回不同的抽象类
		if (IRemoteService.class.getName().equals(intent.getAction())) {
			return mBinder;
		}
		
		return null;
	}

	IRemoteService.Stub mBinder = new IRemoteService.Stub() {
		
		@Override
		public int getPid() throws RemoteException {
			return Process.myPid();
		}
		
		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean,
				float aFloat, double aDouble, String aString)
				throws RemoteException {
			// TODO 对不同的参数类型进行操作
			
		}
	};
}
