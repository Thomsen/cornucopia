package com.cornucopia.application.exception;

import android.content.Context;

/**
 * @author Thomsen
 * @version 1.0
 * @since 12/2/12 12:12 PM
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private static CrashHandler instance = new CrashHandler();

    private Thread.UncaughtExceptionHandler mExceptionHandler;

    private Context mContext;

    private OnHandleListener onHandleListener;

    public static CrashHandler getInstance() {
        return instance;
    }

    public void init(Context context) {     // is private , error
        mContext = context;
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setOnHandleException(OnHandleListener handleListener) {
        if (handleListener == null) {
            throw new CorncopiaException();
        } else {
            onHandleListener = handleListener;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (onHandleListener != null) {
            onHandleListener.handle();
        }
    }

    public interface OnHandleListener {
        public void handle();
    }
}
