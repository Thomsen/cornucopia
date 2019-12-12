package com.cornucopia.lifecycle;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cornucopia.R;

/**
 * Created by thom on 9/5/2017.
 */

public class FragmentLifecycle extends Fragment {

    private static final String TAG = "lifecycle";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.i(TAG, "fragment attach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "fragment create");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "fragmen create view");

//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.activity_fragment_lifecycle, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "fragment activity created");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "fragment start");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "fragment resume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "fragment pause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i(TAG, "fragment stop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.i(TAG, "fragment destroy view");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "fragment destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "fragment detach");
    }
}
