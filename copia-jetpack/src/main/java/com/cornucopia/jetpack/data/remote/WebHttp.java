package com.cornucopia.jetpack.data.remote;


import com.cornucopia.jetpack.data.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thom on 25/5/2017.
 */

public interface WebHttp {

    @GET("/user/{user}")
    Call<User> getUser(@Path("user") String userId);
}
