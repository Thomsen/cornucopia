package com.cornucopia.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import android.util.Log;

@Aspect
public class ActivityStatisAspect {

	public static final String TAG = ActivityStatisAspect.class.getName();
	
	@Pointcut("execution(* android.app.Activity.onCreate(..))")
	public void onCreateEntry() {
		
	}
	
	@Before("onCreateEntry()")
	public void onBeforeCreate(JoinPoint joinPoint) {
		log("before create");
	}
	
	@After("onCreateEntry()")
	public void onAfterCreate(JoinPoint joinPoint) {
		log("after create");
	}
	
	private void log(String msg) {
		Log.d(TAG, " ======> " + msg);
	}
	
}
