package com.cornucopia.service;

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
		
		Toast.makeText(this, "remote service created", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 不同的接口返回不同的抽象类
		if (IRemoteService.class.getName().equals(intent.getAction())) {
			try {
				mBinder.asBinder().linkToDeath(deathRecipient, 0);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
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

	/**
	 * Binder可能会意外死忙（比如Service Crash），Client监听到Binder死忙后可以进行重连服务等操作
	 */
	IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
		@Override
		public void binderDied() {
			if (mBinder != null) {
				mBinder.asBinder().unlinkToDeath(this, 0);
				mBinder = null;
			}

			// TODO: 重连服务或其他操作
		}
	};
}
