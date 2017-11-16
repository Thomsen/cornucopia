package com.cornucoppia.component;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thom on 25/5/2017.
 */

public class UserProfileFragment extends LifecycleFragment {

    private static final String UID_KEY = "uid";

    private UserProfileViewModel viewModel;

    public UserProfileFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(UID_KEY, "0");
        setArguments(bundle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);

        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);

        // java.lang.Class<com.cornucoppia.component.UserProfileViewModel> has no zero argument constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
