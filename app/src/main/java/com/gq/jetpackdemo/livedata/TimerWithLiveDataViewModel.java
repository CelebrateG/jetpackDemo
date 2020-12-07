package com.gq.jetpackdemo.livedata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Timer;
import java.util.TimerTask;

public class TimerWithLiveDataViewModel extends ViewModel {

    @Override
    protected void onCleared() {
        super.onCleared();
        currentSecond = null;
    }

    /**
     * 将 currentSecond 字段用 MutableLiveData 包装起来
     */
    private MutableLiveData<Integer> currentSecond;
    private Timer timer;
    private int currentSeconds;

    public LiveData<Integer> getCurrentSecond() {
        if (currentSecond == null) {
            currentSecond = new MutableLiveData<>();
        }
        return currentSecond;
    }


    public void startTimer() {
        if (timer == null) {
            currentSeconds = 0;
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //此线程非 UI 线程
                    currentSeconds++;
                    if (currentSecond == null) {
                        currentSecond = new MutableLiveData<>();
                    }
                    //非 UI 线程使用 postValue
                    currentSecond.postValue(currentSeconds);
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }
}
