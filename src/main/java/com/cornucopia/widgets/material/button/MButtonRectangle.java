package com.cornucopia.widgets.material.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cornucopia.R;
import com.cornucopia.widgets.material.MaterialUtils;

public class MButtonRectangle extends MButton {

    protected TextView mTextButton;
    
    public MButtonRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onInitDefaultValues() {
        super.onInitDefaultValues();
        
        mTextButton = new TextView(getContext());
        rippleSpeed = 23.5f;
//        minWidth = 80;
//        minHeight = 36;
        backgroundResId = R.drawable.background_button_rectangle;
    }
    
    @Override
    protected void onInitAttributes(AttributeSet attrs) {
        super.onInitAttributes(attrs);
        
        int textResource = attrs.getAttributeResourceValue(ANDROIDXML,"text",-1);
        String text = "";
        if(textResource != -1){
            text = getResources().getString(textResource);
        }
        mTextButton.setText(text);
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        params.setMargins(MaterialUtils.dpToPx(5, getResources()), 
                MaterialUtils.dpToPx(5, getResources()),
                MaterialUtils.dpToPx(5, getResources()), 
                MaterialUtils.dpToPx(5, getResources()));
        mTextButton.setLayoutParams(params);
        
        addView(mTextButton);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x != -1) {
            Rect src = new Rect(0, 0,
                    getWidth()-MaterialUtils.dpToPx(0, getResources()),
                    getHeight()-MaterialUtils.dpToPx(0, getResources()));
            Rect dst = new Rect(MaterialUtils.dpToPx(0, getResources()),
                    MaterialUtils.dpToPx(0, getResources()),
                    getWidth()-MaterialUtils.dpToPx(0, getResources()),
                    getHeight()-MaterialUtils.dpToPx(0, getResources()));
            canvas.drawBitmap(makeCircle(), src, dst, null);
        }
        invalidate();
    }

}
