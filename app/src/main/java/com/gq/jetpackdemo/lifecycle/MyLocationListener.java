package com.gq.jetpackdemo.lifecycle;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class MyLocationListener implements LifecycleObserver {
    private OnLocationChangedListener changedListener;

    private static final String TAG = "MyLocationListener";

    public MyLocationListener(OnLocationChangedListener changedListener) {
        this.changedListener = changedListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void startGetLocation() {
        Log.d(TAG, "startGetLocation: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stopGetLocation() {
        Log.d(TAG, "stopGetLocation: ");
    }

    public interface OnLocationChangedListener {
        /**
         * @param latitude
         * @param longitude
         */
        void onChanged(double latitude, double longitude);
    }
}
