package com.cornucopia.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonRequest<T> extends JsonRequest<T> {
    
    private Gson mGson;
    
    private Type mRespType;
    
    private Map<String, String> mHttpHeaders;
    
    private Response.Listener<T> mResponseListener;

    public GsonRequest(int method, String url, TypeToken<T> respTypeOf, Map<String, String> headers,
            JSONObject jsonRequest, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, ((jsonRequest == null) ? null : jsonRequest.toString()), listener, errorListener);
        
        mGson = new Gson();
        mRespType = ((respTypeOf == null? null : respTypeOf.getType()));
        mHttpHeaders = headers;
        mResponseListener = listener;
        
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(mGson.fromJson(json, mRespType),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        mResponseListener.onResponse(response);
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHttpHeaders == null ? super.getHeaders() : mHttpHeaders;
    }

}
