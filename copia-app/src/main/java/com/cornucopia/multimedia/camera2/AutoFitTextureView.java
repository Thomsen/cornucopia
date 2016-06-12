package com.cornucopia.multimedia.camera2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * conent stream: video or opengl
 *
 */
public class AutoFitTextureView extends TextureView {


    private int mRatioWidth;
    private int mRatioHeight;

    @SuppressLint("NewApi") public AutoFitTextureView(Context context, AttributeSet attrs,
         int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    

    public AutoFitTextureView(Context context) {
        this(context, null);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < (height * mRatioWidth / mRatioHeight) )  {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }
    
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        
        mRatioWidth = width;
        mRatioHeight = height;
        
        requestLayout();
    }

}
