package com.cornucopia.lifecycle.launch;//
// Created by Thomsen on 30/03/2022.
// Copyright (c) 2022 Ricepo LLC. All rights reserved.
//

import com.cornucopia.lifecycle.LaunchModeActivity;

public class LaunchResultActivity extends LaunchModeActivity {


    @Override
    public void onContentChanged() {
        TAG_ACTIVITY_LIFECYCLE = "lifecycle_launch_result";
        super.onContentChanged();
    }

}