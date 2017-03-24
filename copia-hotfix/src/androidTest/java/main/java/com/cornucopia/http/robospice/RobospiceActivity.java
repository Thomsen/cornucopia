package com.cornucopia.http.robospice;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cornucopia.R;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;
import com.octo.android.robospice.request.simple.BitmapRequest;
import com.octo.android.robospice.request.simple.SimpleTextRequest;

import java.io.File;

public class RobospiceActivity extends BaseRobospiceActivity {


    private TextView mTvRobospice;
    private ImageView mIvRobospice;

    private SimpleTextRequest textRequext;
    private BitmapRequest imageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_robospice);

        mTvRobospice = (TextView) findViewById(R.id.tv_robospice);
        mIvRobospice = (ImageView) findViewById(R.id.iv_robospice);

//        textRequext = new SimpleTextRequest("http://baike.baidu.com/view/2096490.htm");
        textRequext = new SimpleTextRequest("http://www.loremipsum.de/downloads/original.txt");
        File cacheFile = new File(getCacheDir(), "guomin.jpg");
//        imageRequest = new BitmapRequest("http://baike.baidu.com/picture/2096490/2096490/0/" +
//        		"a992e31f908b5952314e153d.html?fr=lemma&ct=single#aid=0&pic=a992e31f908b5952314e153d"
//                , cacheFile);
        imageRequest = new BitmapRequest("http://earthobservatory.nasa.gov/blogs/" +
        		"elegantfigures/files/2011/10/globe_west_2048.jpg", cacheFile);

    }

    @Override
    protected void onStart() {
        super.onStart();

        setProgressBarIndeterminate(false);
        setProgressBarVisibility(true);

        spiceManager.execute(textRequext, "txt", DurationInMillis.ONE_MINUTE, new TextReqListener());
        spiceManager.execute(imageRequest, new ImageReqListener());
    }

    @Override
    protected void onStop() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIvRobospice.getDrawable();
        if (bitmapDrawable != null) {
            bitmapDrawable.getBitmap().recycle();
        }
        super.onStop();
    }

    public class TextReqListener implements RequestListener<String>, RequestProgressListener {

        @Override
        public void onRequestFailure(SpiceException excp) {
            excp.printStackTrace();
        }

        @Override
        public void onRequestSuccess(String result) {
            StringBuilder bui = new StringBuilder("text: ");
            bui.append(result);
            mTvRobospice.setText(bui.toString());
        }

        @Override
        public void onRequestProgressUpdate(RequestProgress progress) {
            switch (progress.getStatus()) {  // pending read_from_cache loading_from_network
                case LOADING_FROM_NETWORK: {
                    setProgressBarIndeterminate(false);
                    setProgress((int) (progress.getProgress() * 10000));
                    break;
                }
                default: {
                    break;
                }
            }
        }

    }

    public class ImageReqListener implements RequestListener<Bitmap>, RequestProgressListener {

        @Override
        public void onRequestFailure(SpiceException excp) {
            excp.printStackTrace();
        }

        @Override
        public void onRequestSuccess(Bitmap result) {
            mIvRobospice.setImageBitmap(result);
            setProgressBarIndeterminate(false);
            setProgressBarVisibility(false);
        }

        @Override
        public void onRequestProgressUpdate(RequestProgress progress) {
            switch (progress.getStatus()) {
                case LOADING_FROM_NETWORK: {
                    setProgressBarIndeterminate(false);
                    setProgress((int) (progress.getProgress() * 10000));
                    break;
                }
                default: {
                    break;
                }
            }
        }



    }
}
