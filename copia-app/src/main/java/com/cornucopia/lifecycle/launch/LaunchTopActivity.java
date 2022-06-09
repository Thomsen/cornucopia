package com.cornucopia.lifecycle.launch;//
// Created by Thomsen on 30/03/2022.
// Copyright (c) 2022 Ricepo LLC. All rights reserved.
//

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cornucopia.R;
import com.cornucopia.lifecycle.LaunchModeActivity;

public class LaunchTopActivity extends LaunchModeActivity {


    @Override
    public void onContentChanged() {
        TAG_ACTIVITY_LIFECYCLE = "lifecycle_launch_top";
        super.onContentChanged();
    }

}