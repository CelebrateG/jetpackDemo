package com.gq.jetpackdemo.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyServiceObserver implements LifecycleObserver {

    private String TAG = this.getClass().getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void startGetLocationCreate() {
        Log.d(TAG, "startGetLocationCreate: ");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void stopGetLocationDestroy() {
        Log.d(TAG, "stopGetLocationDestroy: ");
    }
}
