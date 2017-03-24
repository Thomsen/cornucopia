package com.cornucopia.http.okhttp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cornucopia.utils.StringUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpOpt {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    private Context mContext;
    
    public OkHttpOpt(Context context) {
        mContext = context;
    }
    
    public String okHttpGet(String url) {
        String result = "";
        
        Request request = new Request.Builder()
            .url(url)
            .build();
        
        OkHttpClient client = new OkHttpClient();
        try {
            Response resp = client.newCall(request).execute();
            result = StringUtils.convertStreamToString(resp.body().byteStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public String okHttpAsyncGet(String url) {
        String result = "";
        
        Request request = new Request.Builder()
            .url(url)
            .build();
        
        OkHttpClient client = new OkHttpClient();
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Request arg0, IOException arg1) {
//
//            }
//
//            @Override
//            public void onResponse(Response resp) throws IOException {
//                String rst = StringUtils.convertStreamToString(resp.body().byteStream());
//                Log.d("thom", "ok http async resp: " + rst);
//            }
//
//        });
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public String okHttpJsonPost(String url, String json) {
        String result = "";
        
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        
        OkHttpClient client = new OkHttpClient();
        try {
            Response resp = client.newCall(request).execute();
            result = StringUtils.convertStreamToString(resp.body().byteStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public String okHttpLogin(String url, String username, String password) {
        String result = "";
        
//        RequestBody body = new FormEncodingBuilder()
//            .add("username", username)
//            .add("password", password)
//            .build();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .build();
        
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String rst = StringUtils.convertStreamToString(response.body().byteStream());
                Log.d("thom", "ok http login resp: " + rst);
                if (mContext instanceof Activity) {
                    Activity act = (Activity) mContext;
                    // looper.prepare
                    act.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(mContext, rst, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        
        return result;
    }
    

}
