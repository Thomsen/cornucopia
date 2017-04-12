package com.cornucopia.http;

import com.google.gson.Gson;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cornucopia.R;
import com.cornucopia.http.mibo.MiboUser;
import com.cornucopia.http.okhttp.OkHttpOpt;
import com.cornucopia.http.retrofit.MiboRetrofitService;
import com.cornucopia.http.retrofit.RetrofitOpt;
import com.cornucopia.http.volley.VolleyOpt;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class MiboLoginActivity extends Activity implements OnClickListener {
    
    private static final String TAG = "MiboLoginActivity";
    
    private EditText mEtServerAddress;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnConfirm;
    
    private Button mBtnShowUser;
    
//    private String serverAddress = "http://192.168.1.116:3000";
    private String serverAddress = "http://mibo-api.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login_test);
        
        mEtServerAddress = (EditText) findViewById(R.id.et_server_address);
        mEtUsername = (EditText) findViewById(R.id.et_login_username);
        mEtPassword = (EditText) findViewById(R.id.et_login_password);
        mBtnConfirm = (Button) findViewById(R.id.btn_login_confirm);
        mBtnShowUser = (Button) findViewById(R.id.btn_show_user);
        
        mBtnConfirm.setOnClickListener(this);
        mBtnShowUser.setOnClickListener(this);
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
            new Thread() {
                public void run() {
//                	login();
                }
            }.start();
//            volleyLogin();
//            okHttpLogin();
        }
        if (R.id.btn_show_user == v.getId()) {
//            volleyShow();
//            retrofitShow();
            retrofitListUser();
        }
    }
    
//    private void show() {
//    	String showUrl = serverAddress + "/users/17";
//    	HttpParams params = new BasicHttpParams();
////    	params.setIntParameter("id", 17);
//    	HttpOpt hopt = new HttpOpt(this);
//    	hopt.httpGet(showUrl, params);
//    }
    
//    private void login() {
//    	String loginUrl = serverAddress + "/users/login";
//    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
//    	params.add(new BasicNameValuePair("username", mEtUsername.getText().toString()));
//    	params.add(new BasicNameValuePair("password", mEtPassword.getText().toString()));
//    	HttpOpt hopt = new HttpOpt(this);
//    	hopt.httpPost(loginUrl, params);
//    }
    
    private void volleyShow() {
        String showUrl = serverAddress + "/users/6";
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
                Log.d(TAG, "ok http resp: " + resp);
            }
        }.start();
    }
    
    private void okHttpLogin() {
        OkHttpOpt oopt = new OkHttpOpt(this);
        String loginUrl = serverAddress + "/users/login";
        oopt.okHttpLogin(loginUrl, mEtUsername.getText().toString(),
                mEtPassword.getText().toString());
    }

    private void retrofitListUser() {
        RetrofitOpt retrofitOpt = new RetrofitOpt(serverAddress);
        MiboRetrofitService miboRetrofitService = retrofitOpt.getMiboRetrofitService();
        miboRetrofitService.listUsers().enqueue(new Callback<List<MiboUser>>() {
            @Override
            public void onResponse(Call<List<MiboUser>> call, Response<List<MiboUser>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MiboLoginActivity.this, "all: " + new Gson().toJson(response.body()),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MiboUser>> call, Throwable t) {

            }
        });
    }
    
    private void retrofitShow() {
        String showUrl = serverAddress;
        RetrofitOpt retrofitOpt = new RetrofitOpt(showUrl);
        MiboRetrofitService miboService = retrofitOpt.getMiboRetrofitService();
        miboService.findUser("6").enqueue(new Callback<MiboUser>() {

            @Override
            public void onFailure(Call<MiboUser> arg0, Throwable throwable) {
                Log.e(TAG, "retrofit find user " + throwable);
            }

            @Override
            public void onResponse(Call<MiboUser> arg0,
                    retrofit2.Response<MiboUser> response) {
                if (response.isSuccessful()) {
                    MiboUser user = response.body();
                    String message = "retrofit find user " + user.getUsername(); 
                    Log.e(TAG, message);
                    Toast.makeText(MiboLoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }
            
        });;
        
    }
    
}
