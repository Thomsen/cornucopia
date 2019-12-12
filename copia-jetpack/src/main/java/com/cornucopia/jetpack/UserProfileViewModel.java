package com.cornucopia.jetpack;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cornucopia.jetpack.data.UserRepository;
import com.cornucopia.jetpack.data.model.User;

/**
 * Created by thom on 25/5/2017.
 */

public class UserProfileViewModel extends ViewModel {

    private String userId;

    private LiveData<User> user;

    private UserRepository userRepo;

    public UserProfileViewModel() {

    }

    @javax.inject.Inject
    public UserProfileViewModel(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void init(String userId) {
        if (null != this.user || null == userRepo) {
            return ;
        }
        user = userRepo.getUser(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
