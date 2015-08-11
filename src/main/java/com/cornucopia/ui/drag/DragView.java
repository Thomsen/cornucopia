package com.cornucopia.ui.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.cornucopia.R;

public class DragView extends FrameLayout {

	private static final String TAG = "DragView";
	
	private float X;  
    private float Y;  
      
    private View mDragView;
      	    
	public DragView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		mDragView = new View(context);  
//        mDragView.setLayoutParams(new LayoutParams(60, 60));
        mDragView.setLayoutParams(new LayoutParams(160, 160));
        mDragView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_list_collapse));  
        mDragView.setVisibility(View.INVISIBLE);
        addView(mDragView);
	}

	public DragView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DragView(Context context) {
		this(context, null);
	}
	
	public boolean onTouchEvent(MotionEvent ev) {  
        final int action = ev.getAction();  
        X = ev.getX();  
        Y = ev.getY();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            Log.d(TAG, "onTouchEvent ACTION_DOWN");  
            mDragView.layout((int)X - 30, (int)Y - 30, (int)X + 30, (int)Y + 30);  
            mDragView.setVisibility(View.VISIBLE);  
            break;  
        case MotionEvent.ACTION_MOVE:  
            Log.d(TAG, "onTouchEvent ACTION_MOVE x:" + X + " Y:" + Y);  
            mDragView.layout((int)X - 30, (int)Y - 30, (int)X + 30, (int)Y + 30);  
            break;  
        case MotionEvent.ACTION_UP:  
            Log.d(TAG, "onTouchEvent ACTION_UP");  
            mDragView.setVisibility(View.INVISIBLE);  
            break;  
        }  
          
        return true;  
    }

}
