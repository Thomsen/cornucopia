package com.cornucopia.patch;

public class BSPatch {
    
    static {
        try {
            System.loadLibrary("copia-patch");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static native int mergePatch(String oldPath, String newPath, String patchPath);

}
