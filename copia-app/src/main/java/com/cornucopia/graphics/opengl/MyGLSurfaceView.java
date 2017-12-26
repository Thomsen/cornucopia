package com.cornucopia.graphics.opengl;

import android.content.Context;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by thom on 26/12/2017.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        setRenderer(mRenderer);

        // render the view only when there is a change in the drawing data
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);  // touch need

    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;

    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE: {
//
//                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;
//
//                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -1;
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -1;
//                }
//
//                mRenderer.setAngle(mRenderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
//
//                requestRender();
//
//                break;
//            }
//        }
//
//        mPreviousX = x;
//        mPreviousY = y;

        return super.onTouchEvent(event);
    }

}
