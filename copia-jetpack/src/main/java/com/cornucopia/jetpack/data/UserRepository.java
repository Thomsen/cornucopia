package com.cornucopia.jetpack.data;

import androidx.lifecycle.LiveData;

import com.cornucopia.jetpack.data.model.User;
import com.cornucopia.jetpack.data.persist.UserDao;
import com.cornucopia.jetpack.data.remote.WebHttp;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import retrofit2.Response;

/**
 * Created by thom on 25/5/2017.
 */
@Singleton
public class UserRepository {

    private static final int FRESH_TIMEOUT = 50000;

    private final WebHttp webHttp;

    private UserCache userCache;

    private final UserDao userDao;

    private final Executor executor;

    public UserRepository(WebHttp webHttp, UserDao userDao, Executor executor) {
        this.webHttp = webHttp;
        this.userDao = userDao;
        this.executor = executor;
    }

    public LiveData<User> getUser(String userId) {
        LiveData<User> cached = null;
//        cached = userCache.get(userId);
        return cached;
    }

    private void refreshUser(final String userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
//                boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
                boolean userExists = true;
                if (!userExists) {
                    Response response = null;
                    try {
                        response = webHttp.getUser(userId).execute();
                        userDao.save((User) response.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
}
