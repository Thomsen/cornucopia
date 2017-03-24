package com.cornucopia.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cornucopia.patch.BSPatch;
import com.cornucopia.patch.R;
import com.cornucopia.patch.R.id;
import com.cornucopia.patch.R.layout;
import com.cornucopia.patch.R.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class PatchMainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_main);
        
        findViewById(R.id.btn_patch).setOnClickListener(this);
        
        new BSPatch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patch_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (R.id.btn_patch == view.getId()) {
            mergePatch();
        }
    }

    private void mergePatch() {
        File oldFile = copyFile("v1.apk", "v1.apk");
        File patchFile = copyFile("patch_v1_v2.patch", "patch_v1_v2.patch");
        File newFile = new File(patchFile.getParentFile().getPath(), "v2.apk");
        
        try {

            BSPatch.mergePatch(oldFile.getAbsolutePath(),
                    newFile.getAbsolutePath(), patchFile.getAbsolutePath());
            
            intentInstaLL(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void intentInstaLL(File apkFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private File copyFile(String oldFileName, String newFileName) {
        File file = null;
        
        try {
            InputStream inStream = getAssets().open(oldFileName);
            File dirFile = null;
            
//            if (Environment.isExternalStorageEmulated()) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                dirFile = getExternalFilesDir(null);
                if (null == dirFile) {
                    dirFile = getFilesDir();
                }
            } else {
                dirFile = getFilesDir();  // 没有权限合并
            }
            
            
            if (! dirFile.exists()) {
                dirFile.mkdir();
            }
            file = new File(dirFile, newFileName);
            FileOutputStream fos = new FileOutputStream(file);
            
            byte[] buffer = new byte[1024];
            while ((inStream.read(buffer)) != -1) {
                fos.write(buffer);
            }
            
            inStream.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }
}
