package com.gq.jetpackdemo.lifecycle;

import androidx.lifecycle.LifecycleService;

public class MyService extends LifecycleService {

    private String TAG = this.getClass().getName();
    private MyServiceObserver serviceObserver;

    public MyService() {
        serviceObserver = new MyServiceObserver();
        getLifecycle().addObserver(serviceObserver);
    }
}
