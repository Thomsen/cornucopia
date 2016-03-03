package com.cornucopia.http.robospice;

import android.app.Activity;
import android.os.Bundle;

import com.octo.android.robospice.SpiceManager;

public class BaseRobospiceActivity extends Activity {

    protected SpiceManager spiceManager = new SpiceManager(RobospiceService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

}
