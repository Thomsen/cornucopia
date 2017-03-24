package com.cornucopia.ui.html;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class DefineChromeClient extends WebChromeClient {

    private Context mContext;

    public DefineChromeClient(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Toast.makeText(mContext, "alert " + message, Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(mContext).
                setTitle("Alert").setMessage(message).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //
                            }
                        }).create().show();
        result.confirm();
//        return super.onJsAlert(view, url, message, result);
        return true;  // 取代默认的dialog
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Toast.makeText(mContext, "confirm " + message, Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(mContext).
                setTitle("Confirm").setMessage(message).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //
                            }
                        }).setNegativeButton("Cancel", null).create().show();
        result.confirm();
//        return super.onJsConfirm(view, url, message, result);
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
            JsPromptResult result) {
        Toast.makeText(mContext, "prompt " + message, Toast.LENGTH_SHORT).show();
        Log.i("thom", "prompt url " + url);
        Log.i("thom", "prompt defaultValue " + defaultValue);
        // no dialog handle
        result.confirm(); // continue handle
        return true;
    }

}
