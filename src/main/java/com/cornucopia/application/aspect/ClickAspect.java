package com.cornucopia.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import android.util.Log;
import android.widget.Button;

@Aspect
public class ClickAspect {

	public static final String TAG = ClickAspect.class.getName();
	
	@Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
	public void onClickEntryPoint() {
		
	}
	
	@Before("onClickEntryPoint()")
	public void onClickBefore(JoinPoint jointPoint) {
		Log.d(TAG, "Before Advice ==> Clicked on : " + ((Button) jointPoint.getArgs()[0]).getText());
	}
}
