package com.cornucopia.widgets.material.button;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.util.AttributeSet;

import com.cornucopia.widgets.material.MaterialUtils;

public class MButton extends RippleView {

    public MButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitAttributes(attrs);
    }

    protected void onInitAttributes(AttributeSet attrs) {
        setAttributes(attrs);   
    }

    public Bitmap makeCircle() {
        // 画涟漪时要考虑到按钮的边界区域，不要把按钮的阴影边界也填满了
        Bitmap output = Bitmap.createBitmap(
                getWidth() - MaterialUtils.dpToPx(0, getResources()), 
                getHeight() - MaterialUtils.dpToPx(0, getResources()),
                Config.ARGB_8888);
        return makeCircleFromBitmap(output);
    }
    
    @Override
    protected void onInitDefaultValues() {
        backgroundColor = Color.parseColor("#2196f3");
    }
    
    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        backgroundColor = color;
        if (isEnabled()) {
            beforeBackground = backgroundColor;
        } else {
            
        }
    }

}
