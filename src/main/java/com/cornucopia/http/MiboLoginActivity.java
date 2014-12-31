package com.cornucopia.http;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cornucopia.R;
import com.cornucopia.http.appache.HttpOpt;
import com.cornucopia.http.okhttp.OkHttpOpt;
import com.cornucopia.http.volley.GsonRequest;
import com.cornucopia.http.volley.MiboUser;
import com.cornucopia.http.volley.VolleyOpt;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiboLoginActivity extends Activity implements OnClickListener {
    
    private EditText mEtServerAddress;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnConfirm;
    
    private String serverAddress = "http://192.168.1.116:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login_test);
        
        mEtServerAddress = (EditText) findViewById(R.id.et_server_address);
        mEtUsername = (EditText) findViewById(R.id.et_login_username);
        mEtPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnConfirm = (Button) findViewById(R.id.btn_login_confirm);
        
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_login_confirm == v.getId()) {
            if (!TextUtils.isEmpty(mEtServerAddress.getText())) {
                String addr = mEtServerAddress.getText().toString();
                if (addr.startsWith("http://")) {
                    serverAddress = addr;
                } else {
                    serverAddress = "http://" + addr;
                }
                
            }
//            new Thread() {
//                public void run() {
//                	login();
//                }
//            }.start();
//            volleyLogin();
            okHttpLogin();
        }
    }
    
    private void show() {
    	String showUrl = serverAddress + "/users/17";
    	HttpParams params = new BasicHttpParams();
//    	params.setIntParameter("id", 17);
    	HttpOpt hopt = new HttpOpt(this);
    	hopt.httpGet(showUrl, params);
    }
    
    private void login() {
    	String loginUrl = serverAddress + "/users/login";
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("username", mEtUsername.getText().toString()));
    	params.add(new BasicNameValuePair("password", mEtPassword.getText().toString()));
    	HttpOpt hopt = new HttpOpt(this);
    	hopt.httpPost(loginUrl, params);
    }
    
    private void volleyShow() {
        String showUrl = serverAddress + "/users/1";
        JSONObject jsonRequest = new JSONObject();
        VolleyOpt vopt = new VolleyOpt(this);
        vopt.volleyGet(showUrl, jsonRequest);
    }
    
    private void volleyLogin() {
        VolleyOpt vopt = new VolleyOpt(this);
        String loginUrl = serverAddress + "/users/login";
        vopt.volleyLogin(loginUrl, mEtUsername.getText().toString(),
                mEtPassword.getText().toString());
    }
    
    private void okHttpShow() {
        new Thread() {
            public void run() {
                String showUrl = serverAddress + "/users/6";
                OkHttpOpt oopt = new OkHttpOpt(MiboLoginActivity.this);
                String resp = oopt.okHttpGet(showUrl);
                Log.d("thom", "ok http resp: " + resp);
            }
        }.start();
    }
    
    private void okHttpLogin() {
        OkHttpOpt oopt = new OkHttpOpt(this);
        String loginUrl = serverAddress + "/users/login";
        oopt.okHttpLogin(loginUrl, mEtUsername.getText().toString(),
                mEtPassword.getText().toString());
    }
    
}
