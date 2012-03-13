package com.cornucopia.basic.service;

import android.os.IBinder;
import android.os.RemoteException;

public class RemoteImService implements IRemoteService {

	@Override
	public IBinder asBinder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPid() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void basicTypes(int anInt, long aLong, boolean aBoolean,
			float aFloat, double aDouble, String aString)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
