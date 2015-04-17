package com.cornucopia.graphics.fresco;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.cornucopia.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class FrescoDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_fresco_demo);

        Uri uri = Uri.parse("http://frescolib.org/static/fresco-logo.png");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdv_image_view);
        draweeView.setImageURI(uri);

        // Couldn't load gnustl_shared: findLibrary returned null
        // imagepipeline jni libs
        // 解决方式：在cff_imagepipeline做库工程，将jni的so库拷贝一份给libs。让该工程依赖imagepipeline库工程。
    }

}
