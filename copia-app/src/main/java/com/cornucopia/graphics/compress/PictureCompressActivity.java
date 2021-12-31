package com.cornucopia.graphics.compress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.fragment.app.FragmentActivity;

import com.cornucopia.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureCompressActivity extends FragmentActivity {

    private static final int TAKE_PICTURE = 023;

    private ImageView mImageOriginal;
    private ImageView mImageGrayCompress;
    private ImageView mImageRgbCompress;
    private ImageView mImageYuvCompress;
    private ImageView mImageAndroidCompress;

    private ViewFlipper mFlipper;

    private File photoFile;

    private static final String IMAGE_FILE_PREFIX = "IMG_";
    private static final String IMAGE_FILE_SUFFIX = ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture_compress);

        mImageOriginal = (ImageView) findViewById(R.id.iv_image_original);
        mImageGrayCompress = (ImageView) findViewById(R.id.iv_image_gray_compress);
        mImageRgbCompress = (ImageView) findViewById(R.id.iv_image_rgb_compress);
        mImageYuvCompress = (ImageView) findViewById(R.id.iv_image_yuv_compress);
        mImageAndroidCompress = (ImageView) findViewById(R.id.iv_image_android_compress);

        startFlipper(this, true);
    }

    public ViewFlipper startFlipper(Context context, boolean firstStart) {
        mFlipper = new ViewFlipper(context);
        mFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mFlipper.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewFlipper flippy = (ViewFlipper) v;
                flippy.showNext();
            }

        });

        return mFlipper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.picture_compress_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_take_picture) {
            try {
                takePicture();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void takePicture() throws IOException {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = touchFile(null);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(intent, TAKE_PICTURE);
    }

    private File touchFile(String appendSuffix) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String imgFileName = IMAGE_FILE_PREFIX + timeStamp +
                    (appendSuffix == null ? "_" : "_" + appendSuffix + "_");
        return File.createTempFile(imgFileName, IMAGE_FILE_SUFFIX, getTempDirectory(this));

    }

    @SuppressLint("NewApi")
    private File getTempDirectory(Context context) {
        File cache = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Android/data/" + context.getPackageName() + "/cache/");
            // getPackageCodePath /data/app/com.cornucopia-1.apk
        } else {
            cache = context.getCacheDir();
        }

        cache.mkdirs();
        return cache;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PICTURE) {
                // data null, not set EXTRA_OUTPUT

//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
//                mImageOriginal.setImageBitmap(bitmap);  // smallImage

                //

//                mImageOriginal.setImageBitmap(scaleBitmap());
//                mImageCompress.setImageBitmap(compressBitmap(scaleBitmap()));  // oom
//                mImageAndroid.setImageBitmap(androidCompressBitmap(scaleBitmap(), 80));

//                Bitmap bitmap = scaleBitmap(photoFile.getAbsolutePath());
//                int height = bitmap.getHeight();
//                int width = bitmap.getWidth();

                RenderPicture renderPic = new RenderPicture(photoFile.getAbsolutePath(), this);
                PictureResults pr = renderPic.getPictureResults();
                Bitmap bitmap = pr.getBitmap();
                int height = pr.getHeight();
                int width = pr.getWidth();

                int[] pixs = new int[height * width];
                bitmap.getPixels(pixs, 0, width, 0, 0, width, height);

//                mImageOriginal.setImageBitmap(bitmap);
                mImageGrayCompress.setImageBitmap(grayCompressBitmap(pixs, height, width, 0));
                mImageRgbCompress.setImageBitmap(rgbCompressBitmap(pixs, height, width, 0));
                mImageYuvCompress.setImageBitmap(yuvCompressBitmap(pixs, height, width, 0));
                mImageAndroidCompress.setImageBitmap(androidCompressBitmap(bitmap, 80));
                bitmap.recycle();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap scaleBitmap(String photoPath) {
        Bitmap bitmap = null;

        int imgWidth = mImageOriginal.getWidth();
        int imgHeight = mImageOriginal.getHeight();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, opts);

        int photoWidth = opts.outWidth;
        int photoHeight = opts.outHeight;

        int[] pix = new int[photoWidth * photoHeight];

        int scaleFactor = 1;
        if ((imgWidth > 0) || (imgHeight > 0)) {
            scaleFactor = Math.min(photoWidth / imgWidth, photoHeight / imgHeight);
        }

        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scaleFactor;
//        opts.inBitmap = true; //

        bitmap = BitmapFactory.decodeFile(photoPath, opts);

//        bitmap.getPixels(pix, 0, photoWidth, 0, 0, photoWidth, photoHeight);

        return bitmap;
    }

    private void addPicGallary(String photoPath) {
        Intent mediaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaIntent.setData(contentUri);
        sendBroadcast(mediaIntent);
    }

    private Bitmap grayCompressBitmap(int[] bmpArray, int h, int w, int matrixChoice) {
        Bitmap compressBitmap = null;

        DCT dct = new DCT();
        Quantization quantRun = new Quantization(this, matrixChoice);

        int[] tmp = quantRun.quantizer(dct.forwardDCT(bmpArray, h, w));
        compressBitmap = dct.reverseDCT(tmp, h, w);
        bitmapToFile(compressBitmap, "gray");

        return compressBitmap;
    }

    private Bitmap rgbCompressBitmap(int[] bmpArray, int h, int w, int matrixChoice) {
        Bitmap compressBitmap = null;

        DCT dct = new DCT();
        Quantization quantRun = new Quantization(this, matrixChoice);

        double[][][] Y = dct.forwardRGBDCT(bmpArray, h, w);

        int[] tmpR = quantRun.quantizer(Y[0]);
        int[] tmpG = quantRun.quantizer(Y[1]);
        int[] tmpB = quantRun.quantizer(Y[2]);

        int[] R = dct.reverseDCT3D(tmpR, h, w);
        int[] G = dct.reverseDCT3D(tmpG, h, w);
        int[] B = dct.reverseDCT3D(tmpB, h, w);

        int[] res = new int[h * w];

        for (int i=0; i<tmpR.length; i++) {
            res[i] = 0xff000000 | (R[i] << 16) | (G[i] << 8) | B[i];
        }

        compressBitmap = Bitmap.createBitmap(res, w, h, Bitmap.Config.ARGB_8888);
        bitmapToFile(compressBitmap, "rgb");

        return compressBitmap;
    }

    private Bitmap yuvCompressBitmap(int[] bmpArray, int h, int w, int matrixChoice) {
        Bitmap compressBitmap = null;

        DCT dct = new DCT();
        Quantization quantRun = new Quantization(this, matrixChoice);

        double[][][] YUV = dct.forwardYUVDCT(bmpArray, h, w);

        int[] tmpY = quantRun.quantizer(YUV[0]);
        int[] tmpU = quantRun.UVquantizer(YUV[1]);
        int[] tmpV = quantRun.UVquantizer(YUV[2]);

        int[] Y = dct.reverseDCT3D(tmpY, h, w);
        int[] U = dct.reverseDCT3D(tmpU, h, w);
        int[] V = dct.reverseDCT3D(tmpV, h, w);

        int[] res = new int[h * w];

        for (int i=0; i<tmpY.length; i++) {
            int R = V[i] + Y[i];
            int B = U[i] + Y[i];
            int G = (int) ((Y[i] - .299 * R - .114 *B) / (.587));

            res[i] = 0xff000000 | (R << 16) | (G << 8) | B;
        }

        compressBitmap = Bitmap.createBitmap(res, w, h, Bitmap.Config.ARGB_8888);
        bitmapToFile(compressBitmap, "yuv");

        return compressBitmap;
    }

    private Bitmap androidCompressBitmap(Bitmap bitmap, int quality) {
        Bitmap compressBitmap = null;

        ByteArrayOutputStream out = null;

        out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, quality, out);

        byte[] res = out.toByteArray();

        byteArrayToFile(res, "android");
        compressBitmap = BitmapFactory.decodeByteArray(res, 0, res.length);

        return compressBitmap;

    }

    private void bitmapToFile(Bitmap bmp, String fileSuffix) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 100, out);

        byte[] res = out.toByteArray();

        byteArrayToFile(res, fileSuffix);
    }

    private void byteArrayToFile(byte[] res, String fileSuffix) {

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(touchFile(fileSuffix)));
            bos.write(res);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 }
