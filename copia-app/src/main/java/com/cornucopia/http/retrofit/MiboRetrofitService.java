package com.cornucopia.http.retrofit;

import com.cornucopia.http.mibo.MiboUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MiboRetrofitService {
    
    @GET("/v1/users")
    Call<List<MiboUser>> listUsers();
    
    @GET("/v1/users/{id}")
    Call<MiboUser> findUser(@Path("id") String id);

}
