package com.cornucopia.ui;

import android.app.Activity;
import android.os.Bundle;

import com.cornucopia.R;

public class ThermometerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_thermometer);
    }
}
