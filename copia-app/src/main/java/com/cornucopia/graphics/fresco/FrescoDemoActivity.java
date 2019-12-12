package com.cornucopia.graphics.fresco;

import android.app.Activity;
import android.graphics.ColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.cornucopia.R;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import java.util.List;

public class FrescoDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_fresco_demo);

        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdv_image_view);
//        loadImageUri(draweeView);
        loadImageController(draweeView);

        // Couldn't load gnustl_shared: findLibrary returned null
        // imagepipeline jni libs
        // 解决方式：在cff_imagepipeline做库工程，将jni的so库拷贝一份给libs。让该工程依赖imagepipeline库工程。

        // width:height 4:3
        draweeView.setAspectRatio(1.33f);

        // ControllerBuilder
        setHierarchyBuilder(draweeView);
    }

    private void loadImageController(SimpleDraweeView draweeView) {
        Uri uri = Uri.parse("http://frescolib.org/static/fresco-logo.png");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, @Nullable
                    ImageInfo imageInfo, @Nullable
                    Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (null == imageInfo) {
                            return;
                        }
                        QualityInfo qualityInfo = imageInfo.getQualityInfo();
                        FLog.d("Final image received! " + "Size %d x %d ", "Quality level %d, good enough: %s, full quality: %s",
                                imageInfo.getWidth(),
                                imageInfo.getHeight(),
                                qualityInfo.getQuality(),
                                qualityInfo.isOfGoodEnoughQuality(),
                                qualityInfo.isOfFullQuality());
                    }
                })
                .build();

        draweeView.setController(controller);
    }


    private void loadImageUri(SimpleDraweeView draweeView) {
        Uri uri = Uri.parse("http://frescolib.org/static/fresco-logo.png");
        draweeView.setImageURI(uri);
    }

    private void setHierarchyBuilder(SimpleDraweeView draweeView) {
        List<Drawable> backgroundsList = null;
        List<Drawable> overlaysList = null;

        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(300)
                .setPlaceholderImage(getResources().getDrawable(R.drawable.loading_flag))
//                .setBackgrounds(backgroundsList)
                .setOverlays(overlaysList)
                .build();

        // colorfilter
        ColorFilter colorFilter = null;
        hierarchy.setActualImageColorFilter(colorFilter);

        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE);
//        PointF focusPoint = null;
//        hierarchy.setActualImageFocusPoint(focusPoint);

        // rounding
        RoundingParams roundingParams = hierarchy.getRoundingParams();
        if (null != roundingParams) {
            roundingParams.setCornersRadius(10);
            hierarchy.setRoundingParams(roundingParams);
        }

        draweeView.setHierarchy(hierarchy);
    }

}
