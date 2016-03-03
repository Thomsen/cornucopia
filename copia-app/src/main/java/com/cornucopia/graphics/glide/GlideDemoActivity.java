package com.cornucopia.graphics.glide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cornucopia.R;

public class GlideDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide_demo);

        ImageView iv = (ImageView) findViewById(R.id.iv_glide_image);
        Glide.with(this).load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
            .into(iv);

    }
}
