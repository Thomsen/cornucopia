package com.cornucopia.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ActivityStatisAspect {

	public static final String TAG = ActivityStatisAspect.class.getName();

	@Pointcut("execution(* android.app.Activity.onCreate(..))")
	public void onCreateEntry() {

	}

	@Before("onCreateEntry()")
	public void onBeforeCreate(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before create");                                      // demo
//		log("kind ", joinPoint.getKind());                             // method-execution
//		log("args ", joinPoint.getArgs().toString());	               // Ljava.lang.Object;@1dd017d1
//		log("signature ", joinPoint.getSignature().toString());        // void com.cornucopia.application.LoadingActivity.onCreate(Bundle)
//		log("source loc ", joinPoint.getSourceLocation().toString());  // LoadingActivity.java:18
//		log("target ", joinPoint.getTarget().toString());              // com.cornucopia.application.LoadingActivity@15820236
//		log("this ", joinPoint.getThis().toString());                  // com.cornucopia.application.LoadingActivity@15820236
//		log("str ", joinPoint.toString());                             // execution(void com.cornucopia.application.LoadingActivity.onCreate(Bundle))
//		log("short str ", joinPoint.toShortString());                  // execution(LoadingActivity.onCreate(..))
//		log("long str ", joinPoint.toLongString());                    // execution(protected void com.cornucopia.application.LoadingActivity.onCreate(android.os.Bundle))
	}

	@After("onCreateEntry()")
	public void onAfterCreate(JoinPoint joinPoint) {
		log(joinPoint.toString(),  " after create");
	}

	@Pointcut("execution(* android.app.Activity.onRestart(..))")
	public void onRestartEntry() {

	}

	@Before("onRestartEntry()")
	public void onBeforeRestart(JoinPoint joinPoint) {
		log(joinPoint.toString(), " before restart");
	}

	@After("onRestartEntry()")
	public void onAfterRestart(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after restart");
	}

	@Pointcut("execution(* android.app.Activity.onStart(..))")
	public void onStartEntry() {

	}

	@Before("onStartEntry()")
	public void onBeforeStart(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before start");
	}

	@After("onStartEntry()")
	public void onAfterStart(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after start");
	}

	@Pointcut("execution(* android.app.Activity.onResume(..))")
	public void onResumeEntry() {
		// should have an empty method body
	}

	@Before("onResumeEntry()")
	public void onBeforeResume(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before resume");
	}

	@After("onResumeEntry()")
	public void onAfterResume(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after resume");
	}

//	@Around("..()")

	@Pointcut("execution(* android.app.Activity.onPause(..))")
	public void onPauseEntry() {

	}

	@Before("onPauseEntry()")
	public void onBeforePause(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before pause");
	}

	@After("onPauseEntry()")
	public void onAfterPause(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after pause");
	}

	@Pointcut("execution(* android.app.Activity.onStop(..))")
	public void onStopEntry() {

	}

	@Before("onStopEntry()")
	public void onBeforeStop(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before stop");
	}

	@After("onStopEntry()")
	public void onAfterStop(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after stop");
	}

	@Pointcut("execution(* android.app.Activity.onDestroy(..))")
	public void onDestroyEntry() {

	}

	@Before("onDestroyEntry()")
	public void onBeforeDestroy(JoinPoint joinPoint) {
		log(joinPoint.toString(), "before destroy");
	}

	@After("onDestroyEntry()")
	public void onAfterDestroy(JoinPoint joinPoint) {
		log(joinPoint.toString(), "after destroy");
	}

	private void log(String prev, String msg) {
		Log.d(TAG, prev + " ======> " + msg);
	}

}
