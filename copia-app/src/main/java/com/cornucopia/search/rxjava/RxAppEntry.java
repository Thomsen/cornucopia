package com.cornucopia.search.rxjava;

import java.io.File;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class RxAppEntry {
    
    public RxAppEntry(Context context, ApplicationInfo info) {
        mInfo = info;
        mApkFile = new File(info.sourceDir);
        this.context = context;
        pkgManager = context.getPackageManager();
    }

    public ApplicationInfo getApplicationInfo() {
        return mInfo;
    }

    public String getLabel() {
        return mLabel;
    }

    public Drawable getIcon() {
        if (mIcon == null) {
            if (mApkFile.exists()) {
                mIcon = mInfo.loadIcon(pkgManager);
                return mIcon;
            } else {
                mMounted = false;
            }
        } else if (!mMounted) {
            // If the app wasn't mounted but is now mounted, reload
            // its icon.
            if (mApkFile.exists()) {
                mMounted = true;
                mIcon = mInfo.loadIcon(pkgManager);
                return mIcon;
            }
        } else {
            return mIcon;
        }

        return context.getResources().getDrawable(
                android.R.drawable.sym_def_app_icon);
    }

    @Override public String toString() {
        return mLabel;
    }

    void loadLabel(Context context) {
        if (mLabel == null || !mMounted) {
            if (!mApkFile.exists()) {
                mMounted = false;
                mLabel = mInfo.packageName;
            } else {
                mMounted = true;
                CharSequence label = mInfo.loadLabel(context.getPackageManager());
                mLabel = label != null ? label.toString() : mInfo.packageName;
            }
        }
    }

    private final Context context;
    private final PackageManager pkgManager;
    private final ApplicationInfo mInfo;
    private final File mApkFile;
    private String mLabel;
    private Drawable mIcon;
    private boolean mMounted;
}
