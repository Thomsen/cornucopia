package com.cornucopia.patch;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class BSPatch {
    
    static {
        try {
            // -- android 4.1
            // cannot load library: link_image[1891]:   785 could not load needed library
            // 'libcopia-bzip2.so' for 'libcopia-patch.so' (load_library[1093]: Library 'libcopia-bzip2.so' not found)  
//            System.loadLibrary("copia-bzip2");  // android.mk include $(BUILD_STATIC_LIBRARY)  merge .a to .so
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
