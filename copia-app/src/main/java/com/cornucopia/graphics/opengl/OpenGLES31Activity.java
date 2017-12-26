package com.cornucopia.graphics.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by thom on 26/12/2017.
 */

public class OpenGLES31Activity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkSupported()) {
            mGLView = new MyGLSurfaceView(this);
            setContentView(mGLView);
        } else {
            Snackbar.make(getCurrentFocus(), "not support opengl es", Snackbar.LENGTH_SHORT).show();
        }
    }


    private boolean checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        return supportsEs2 || isEmulator;
    }

}
