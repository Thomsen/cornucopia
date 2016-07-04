package com.cornucopia.http.retrofit;

import java.util.List;

import com.cornucopia.http.mibo.MiboUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MiboRetrofitService {
    
    @GET("users")
    Call<List<MiboUser>> listUsers();
    
    @GET("users/{id}")
    Call<MiboUser> findUser(@Path("id") String id);

}
