package com.cornucopia.aspect;

import android.util.Log;
import android.widget.Button;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AppClickAspect {

    public static final String TAG = AppClickAspect.class.getName();

    // this aspect not execute, library aspect execute
    @After("onClickEntryPoint()")
    public void onClickAfter(JoinPoint jointPoint) {
        // PictureCompressActivity ViewFlipper cannot be cast to
        if (jointPoint.getArgs()[0] instanceof Button) {
            Log.d(TAG, "App After Advice ==> Clicked on : " + ((Button) jointPoint.getArgs()[0]).getText());
        }
    }
}
