package com.cornucopia.http.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HttpLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response;
    }

}
