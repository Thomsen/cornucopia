package com.cornucopia.http.volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;

public class VolleyOpt {

    private Context mContext;
    
    public VolleyOpt(Context context) {
        mContext = context;
    }
    
    public void volleyLogin(String url, String username, String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        JSONObject jsonRequest = new JSONObject(params);
        TypeToken<MiboUser> typeOf = new TypeToken<MiboUser>(){};
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(new GsonRequest<MiboUser>(Method.POST, url, typeOf, null, jsonRequest, new Response.Listener<MiboUser>() {

            @Override
            public void onResponse(MiboUser response) {
                Log.i("thom", "username " + response.getUsername());
                Toast.makeText(mContext, "username " + response.getUsername(),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    Log.e("thom", "error response timeout");
                }
                Log.e("thom", "error response " + error.getMessage());
            }
        }));
        queue.start();
        
    }
    
 
    
    public void volleyGet(String url, JSONObject jsonRequest) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(new JsonObjectRequest(Method.GET, url, jsonRequest,
          new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("thom", "volley login " + response);
                Toast.makeText(mContext, response.toString(), Toast.LENGTH_SHORT).show();
            }
            
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("thom", "message " + error.getMessage());
            }
        }));
        queue.start();
    }
}
