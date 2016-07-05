package com.cornucopia.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class BSPatch {
    
    static {
        try {
            System.loadLibrary("copia-patch");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static native int mergePatch(String oldPath, String newPath, String patchPath);
    
    private static String getApkFile(Context context) {
        ApplicationInfo ai = context.getApplicationInfo();
        return ai.publicSourceDir;
    }

}
