package com.cornucopia.multimedia.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.widget.Toast;

public class CameraPictureCallback implements PictureCallback {
    
    private Context mContext;
    
    private Uri fileUri;
    
    public CameraPictureCallback(Context context, Uri fileUri) {
        this.mContext = context;
        this.fileUri = fileUri;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        Bitmap source = BitmapFactory.decodeByteArray(data, 0, data.length);
        
        OutputStream outputStream = null;
        
        try {
            File pictureFile = new File(fileUri.getEncodedPath());
            outputStream = new FileOutputStream(pictureFile);
            
            Bitmap bitmapFac = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight());
            bitmapFac.compress(CompressFormat.JPEG, 100, outputStream);
            
            outputStream.close();
            
            Toast.makeText(mContext, fileUri.getEncodedPath() + " picture saved", Toast.LENGTH_SHORT).show();
            
            camera.startPreview();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // fireUri is null NPE
            e.printStackTrace();
        }
    }

}
