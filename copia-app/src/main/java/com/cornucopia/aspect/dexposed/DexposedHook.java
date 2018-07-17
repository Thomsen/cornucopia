package com.cornucopia.aspect.dexposed;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;

public class DexposedHook {
    
    private static final String TAG = "DexposedHook";
    
    public void hook(Context context) {
        if (DexposedBridge.canDexposed(context)) {
            hookClick(context);
        } else {
            Log.i(TAG, "cannot load dexposed native library");
        }
    }
    
    private void hookClick(final Context context) {
        // 接口中方法，实现类中无法监听
        DexposedBridge.findAndHookMethod(View.OnClickListener.class, "onClick", View.class, new XC_MethodHook() {
            
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                super.afterHookedMethod(param);
                
                Log.i(TAG, "  =========================== View.OnClickListener onClick after"); 
                
            }
        });
        
        // 实现类中可以监听到
        DexposedBridge.findAndHookMethod(DexposedActivity.class, "onClick", View.class, new XC_MethodHook() {
            
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                super.afterHookedMethod(param);
                
                Log.i(TAG, " ============================= DexposedActivity onClick after");
                Toast.makeText(context, "dexposed hook click afger", Toast.LENGTH_SHORT).show();
                
            }
        });
        
        // DexposedActivity.class onCreate执行时设置监听，会触发该方法
        DexposedBridge.findAndHookMethod(View.class, "setOnClickListener", View.OnClickListener.class, new XC_MethodHook() {
            
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                super.afterHookedMethod(param);
                
                Log.i(TAG, " ============================== View setOnClickListener after");
            }
        });
        
        
    }

}
