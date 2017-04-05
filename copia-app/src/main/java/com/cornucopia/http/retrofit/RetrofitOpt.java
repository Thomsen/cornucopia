package com.cornucopia.http.retrofit;

import android.util.Log;

import com.cornucopia.http.mibo.MiboUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitOpt {
    
    private MiboRetrofitService miboService;
    
    public RetrofitOpt(String url) {
        
        HttpLoggingInterceptor loggintInterceptor = new HttpLoggingInterceptor();
        
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(loggintInterceptor);
        clientBuilder.readTimeout(20000, TimeUnit.SECONDS);
        
        OkHttpClient client = clientBuilder.build();
        
        Log.i("thom", "okhttp3 client connection timeout " + client.connectTimeoutMillis()
                + " read timeout " + client.readTimeoutMillis());
        
        
        Gson gsonIns = new GsonBuilder().registerTypeAdapter(
                new TypeToken<MiboUser>(){}.getType(), new UserResultsDeserializer())
                .create();
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gsonIns))  // need converter-gson
            .client(client)
            .build();
        
        miboService = retrofit.create(MiboRetrofitService.class);
    }
    
    public MiboRetrofitService getMiboRetrofitService() {
        return miboService;
    }

}
