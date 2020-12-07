package com.gq.jetpackdemo.lifecycle;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

public class MyApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
    }


}
