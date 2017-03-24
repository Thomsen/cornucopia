package com.cornucopia.hotfix;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import android.content.Context;

public class Hotfix {
    
    public void loadBugfix(Context context, String pathDexFile, String pathClassName) {
        // /data/data/com.cornucopia/app_dex
        File dexPath = new File(context.getDir("dex", Context.MODE_PRIVATE), pathDexFile);
        
        // prepare dex
        BufferedInputStream dexBuf = null;
        OutputStream dexOut = null;
        
        try {
            // assets directory
            dexBuf = new BufferedInputStream(context.getAssets().open(pathDexFile));
            dexOut = new BufferedOutputStream(new FileOutputStream(dexPath));
            byte[] buf = new byte[1024];
            
            int len = 0;
            while((len = dexBuf.read(buf, 0, 1024)) > 0) {
                dexOut.write(buf);
            }
            
            dexOut.close();
            dexBuf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        patch(context, dexPath.getAbsolutePath(), pathClassName);
    }

    public void patch(Context context, String patchDexFile, String patchClassName) {
        if (null != patchDexFile && new File(patchDexFile).exists()) {
            
            try {
                if (hasLexClassLoader()) {
                    // aliyun os
                    injectInAliyunOs(context, patchDexFile, patchClassName);
                } else if (hasDexClassLoader()) {
                    // 14 +
                    injectAboveEqualApiLevel14(context, patchDexFile, patchClassName);
                } else {
                    // 14 -
                    injectBelowApiLevel14(context, patchDexFile, patchClassName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    
    private static boolean hasLexClassLoader() {
        try {
            Class.forName("dalvik.system.LexClassLoader");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static boolean hasDexClassLoader() {
        try {
            Class.forName("dalvik.system.BaseDexClassLoader");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    @SuppressWarnings({ "unchecked" })
    private static void injectInAliyunOs(Context context, String patchDexFile,
            String patchClassName) throws ClassNotFoundException,
            NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException,
            NoSuchFieldException {
        PathClassLoader obj = (PathClassLoader) context.getClassLoader();
        String replaceAll = new File(patchDexFile).getName().replaceAll(
                "\\.[a-zA-Z0-9]+", ".lex");
        Class cls = Class.forName("dalvik.system.LexClassLoader");
        Object newInstance = cls.getConstructor(
                new Class[] { String.class, String.class, String.class,
                        ClassLoader.class }).newInstance(
                new Object[] {
                        context.getDir("dex", 0).getAbsolutePath()
                                + File.separator + replaceAll,
                        context.getDir("dex", 0).getAbsolutePath(),
                        patchDexFile, obj });
        cls.getMethod("loadClass", new Class[] { String.class }).invoke(
                newInstance, new Object[] { patchClassName });
        setField(
                obj,
                PathClassLoader.class,
                "mPaths",
                appendArray(getField(obj, PathClassLoader.class, "mPaths"),
                        getField(newInstance, cls, "mRawDexPath")));
        setField(
                obj,
                PathClassLoader.class,
                "mFiles",
                combineArray(getField(obj, PathClassLoader.class, "mFiles"),
                        getField(newInstance, cls, "mFiles")));
        setField(
                obj,
                PathClassLoader.class,
                "mZips",
                combineArray(getField(obj, PathClassLoader.class, "mZips"),
                        getField(newInstance, cls, "mZips")));
        setField(
                obj,
                PathClassLoader.class,
                "mLexs",
                combineArray(getField(obj, PathClassLoader.class, "mLexs"),
                        getField(newInstance, cls, "mDexs")));
    }

    private static void injectBelowApiLevel14(Context context, String patchDexFile,
            String patchClassName) throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException {
        PathClassLoader obj = (PathClassLoader) context.getClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader(patchDexFile, context.getDir(
                "dex", 0).getAbsolutePath(), patchDexFile, context.getClassLoader());
        dexClassLoader.loadClass(patchClassName);
        setField(
                obj,
                PathClassLoader.class,
                "mPaths",
                appendArray(
                        getField(obj, PathClassLoader.class, "mPaths"),
                        getField(dexClassLoader, DexClassLoader.class,
                                "mRawDexPath")));
        setField(
                obj,
                PathClassLoader.class,
                "mFiles",
                combineArray(
                        getField(obj, PathClassLoader.class, "mFiles"),
                        getField(dexClassLoader, DexClassLoader.class, "mFiles")));
        setField(
                obj,
                PathClassLoader.class,
                "mZips",
                combineArray(getField(obj, PathClassLoader.class, "mZips"),
                        getField(dexClassLoader, DexClassLoader.class, "mZips")));
        setField(
                obj,
                PathClassLoader.class,
                "mDexs",
                combineArray(getField(obj, PathClassLoader.class, "mDexs"),
                        getField(dexClassLoader, DexClassLoader.class, "mDexs")));
        obj.loadClass(patchClassName);
    }

    private static void injectAboveEqualApiLevel14(Context context, String patchDexFile,
            String patchClassName) throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException {
        PathClassLoader pathClassLoader = (PathClassLoader) context
                .getClassLoader();
        Object a = combineArray(
                getDexElements(getPathList(pathClassLoader)),
                getDexElements(getPathList(new DexClassLoader(patchDexFile, context
                        .getDir("dex", 0).getAbsolutePath(), patchDexFile, context
                        .getClassLoader()))));
        Object a2 = getPathList(pathClassLoader);
        setField(a2, a2.getClass(), "dexElements", a);
        pathClassLoader.loadClass(patchClassName);
    }

    
    private static void setField(Object obj, Class cls, String str, Object obj2)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }
    
    private static Object getField(Object obj, Class cls, String str)
            throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }
    
    private static Object combineArray(Object obj, Object obj2) {
        Class componentType = obj2.getClass().getComponentType();
        int length = Array.getLength(obj2);
        int length2 = Array.getLength(obj) + length;
        Object newInstance = Array.newInstance(componentType, length2);
        for (int i = 0; i < length2; i++) {
            if (i < length) {
                Array.set(newInstance, i, Array.get(obj2, i));
            } else {
                Array.set(newInstance, i, Array.get(obj, i - length));
            }
        }
        return newInstance;
    }
    
    private static Object appendArray(Object obj, Object obj2) {
        Class componentType = obj.getClass().getComponentType();
        int length = Array.getLength(obj);
        Object newInstance = Array.newInstance(componentType, length + 1);
        Array.set(newInstance, 0, obj2);
        for (int i = 1; i < length + 1; i++) {
            Array.set(newInstance, i, Array.get(obj, i - 1));
        }
        return newInstance;
    }
    
    private static Object getPathList(Object obj)
            throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException {
        return getField(obj, Class.forName("dalvik.system.BaseDexClassLoader"),
                "pathList");
    }
    
    private static Object getDexElements(Object obj)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(obj, obj.getClass(), "dexElements");
    }

}
