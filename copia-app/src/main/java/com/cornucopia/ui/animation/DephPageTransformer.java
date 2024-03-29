package com.cornucopia.ui.animation;

import android.annotation.SuppressLint;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import android.view.View;

public class DephPageTransformer implements PageTransformer {

    private static final float MIN_SCALE = 0.75f;

    @SuppressLint("NewApi")
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) {
            view.setAlpha(0);
        } else if (position <= 0) {
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (position <= -1) {
            view.setAlpha(1 - position);
            view.setTranslationX(pageWidth * -position);  // 抵消默认的整页过渡
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else {
            view.setAlpha(0);
        }
    }

}
