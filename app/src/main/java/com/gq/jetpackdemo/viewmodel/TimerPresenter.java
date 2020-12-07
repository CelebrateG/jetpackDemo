package com.gq.jetpackdemo.viewmodel;

import java.util.Timer;
import java.util.TimerTask;

public class TimerPresenter {

    private Timer timer;
    private int currentSecond;
    private OnTimeChangeListener listener;

    public void setListener(OnTimeChangeListener listener) {
        this.listener = listener;
    }

    public void startTimer() {
        if (timer == null) {
            currentSecond = 0;
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //此线程非 UI 线程
                    System.out.println(Thread.currentThread().getName());
                    currentSecond++;
                    if (listener != null) {
                        listener.onTimeChanged(currentSecond);
                    }
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }


    /**
     * 通过接口的方式对调用者进行通知，更好的方式是通过 LiveData 实现
     */
    public interface OnTimeChangeListener {
        void onTimeChanged(int second);
    }
}
