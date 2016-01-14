package com.cornucopia.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import android.util.Log;

@Aspect
public class LogAspect {
	
	static final String TAG = "LogAspect";
	
	
	@Pointcut("execution(* com.cornucopia.lifecycle.ActivityLifecycle.onCreate(..))")
	public void logForActivity() {
		
	}
	
	@Before("logForActivity()")
	public void log(JoinPoint joinPoint) {
		Log.e(TAG, joinPoint.toShortString());
	}

}
