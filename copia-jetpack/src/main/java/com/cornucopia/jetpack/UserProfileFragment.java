package com.cornucopia.jetpack;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cornucopia.jetpack.lifecycle.UserProfileObserver;

/**
 * Created by thom on 25/5/2017.
 */

public class UserProfileFragment extends Fragment {

    private static final String UID_KEY = "uid";

    private UserProfileViewModel viewModel;

    public UserProfileFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(UID_KEY, "0");
        setArguments(bundle);
        getLifecycle().addObserver(new UserProfileObserver());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String userId = getArguments().getString(UID_KEY);

        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.init(userId);

        // java.lang.Class<com.cornucopia.component.UserProfileViewModel> has no zero argument constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
