package com.gq.jetpackdemo.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.livedata.TimerWithLiveDataViewModel;

/**
 * 屏幕旋转，activity重新创建，viewModel 并不受到影响，且数据仍然存在
 */
public class TimerActivity extends AppCompatActivity {
    private TextView tvTime, liveDataTvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        tvTime = findViewById(R.id.tvTime);
        liveDataTvTime = findViewById(R.id.tvTime1);
        System.out.println("TimerAct onCreate");
//        iniComponent();
//        initPresenter();
        initLiveData();
    }

    public void iniComponent() {
        TimerViewModel viewModel = new ViewModelProvider(this).get(TimerViewModel.class);
        viewModel.setListener(new TimerViewModel.OnTimeChangeListener() {
            @Override
            public void onTimeChanged(final int second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(String.valueOf(second));
                    }
                });
            }
        });
        viewModel.startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("TimerAct onDestroy");
    }

    /**
     * 传统的 presenter 作为对比
     */
    public void initPresenter() {
        TimerPresenter presenter = new TimerPresenter();
        presenter.setListener(new TimerPresenter.OnTimeChangeListener() {
            @Override
            public void onTimeChanged(final int second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(String.valueOf(second));
                    }
                });
            }
        });
        presenter.startTimer();
    }


    public void initLiveData() {
        TimerWithLiveDataViewModel model = new ViewModelProvider(this).get(TimerWithLiveDataViewModel.class);
        final MutableLiveData<Integer> liveData = (MutableLiveData<Integer>) model.getCurrentSecond();
        liveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                liveDataTvTime.setText(String.valueOf(integer));
            }
        });
        findViewById(R.id.resetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // UI 线程使用 setValue，完成对 ViewModel 中数据的更新
                liveData.setValue(0);
            }
        });
        model.startTimer();
    }
}