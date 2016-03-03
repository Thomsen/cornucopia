package com.cornucopia.http.robospice;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.binary.InFileBitmapObjectPersister;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.string.InFileStringObjectPersister;

public class RobospiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();

        InFileStringObjectPersister instrPersister = new InFileStringObjectPersister(application);
        InFileBitmapObjectPersister inbitPersister = new InFileBitmapObjectPersister(application);

        cacheManager.addPersister(instrPersister);
        cacheManager.addPersister(inbitPersister);

        return cacheManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }



}
