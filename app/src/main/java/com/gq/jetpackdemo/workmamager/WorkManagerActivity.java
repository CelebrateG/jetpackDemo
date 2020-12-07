package com.gq.jetpackdemo.workmamager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.gq.jetpackdemo.BR;
import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.databinding.ActivityWorkManagerBinding;
import com.gq.jetpackdemo.databinding.Book;
import com.gq.jetpackdemo.databinding.EventHandleListener;
import com.gq.jetpackdemo.databinding.RecyclerViewAdapter;
import com.gq.jetpackdemo.databinding.TwoWayBindingViewModel;
import com.gq.jetpackdemo.databinding.TwoWayBindingViewModel2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * workManager 调用及 dataBinding 应用
 */
public class WorkManagerActivity extends AppCompatActivity {
    ActivityWorkManagerBinding binding;
    public Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager);
        book = new Book("kobe", "曼巴精神");
        book.setRating(5);
        //绑定数据方式一
        binding.setVariable(BR.book, book);
        //绑定数据方式二
//        binding.setBook(book);

        //在布局文件中设置 onClick 响应之后，在此处绑定监听器,传到布局文件
        binding.setEventHandler(new EventHandleListener(this));

        //非 https 的链接 要在 Manfest 中增加 android:usesCleartextTraffic="true"
        binding.setNetworkImageUrl("https://note.youdao.com/yws/public/resource/81a06b7037788be3ab2c09c8d40c6e92/xmlnote/91F6B85E71814630B36D36B8B208F128/4181");
        binding.setNetworkImageUrl2("");
        binding.setLocalImage(R.drawable.guolicheng);

        //双向绑定方式一
        binding.setViewModel(new TwoWayBindingViewModel());
        //双向绑定方式二
        binding.setViewModel2(new TwoWayBindingViewModel2());


        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        List<Book> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Book("java", "Allen"));
        }
        binding.rv.setHasFixedSize(true);
        binding.rv.setAdapter(new RecyclerViewAdapter(list));
    }


    /**
     * 设置触发条件:
     * 设备处于充电，网络已连接，且电池电量充足的状态下，才执行任务。
     */
    public WorkRequest setConstraints() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadLogWorker.class)
                .setConstraints(constraints)
                .build();
        return request;
    }

    /**
     * 设置延迟执行任务
     */
    public WorkRequest delayExcute() {
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadLogWorker.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build();
        return uploadWorkRequest;
    }

    /**
     * 设置指数退避策略,一段时间后会重试该任务
     */
    public WorkRequest setStrategy() {
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadLogWorker.class)
                //设置任务标签
                .addTag("UploadTag")
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();
        return uploadWorkRequest;
    }

    /**
     * 执行任务配置
     *
     * @param context
     */
    public void excute(Context context) {
        WorkManager.getInstance(context).enqueue(setStrategy());
    }


    /**
     * 取消任务
     */
    public void cancelWork(Context context) {
        WorkManager.getInstance(context).cancelAllWork();
    }

    /**
     * WorkManager 与 Worker 之间的参数传递
     */
    public WorkRequest dataBetweenWorkerAndWorkManager() {
        Data inputData = new Data.Builder().putString("input_data", "hello world!").build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadLogWorker.class)
                .setInputData(inputData)
                .build();
        return request;
    }

    /**
     * 观察任务状态
     * 得到从 Worker 传递过来的数据
     */
    public void watchWorkManager(Context context) {
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(dataBetweenWorkerAndWorkManager().getId()).observe((LifecycleOwner) context, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.d("onChanged()->", "workInfo:" + workInfo);
                if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    String outputData = workInfo.getOutputData().getString("output_data");
                }
            }
        });
    }

    /**
     * 周期性任务，间隔时间不能小于15分钟
     * 周期任务 workInfo 不会受到 Success,Failure 通知
     */
    public WorkRequest getPeriodicWorkRequest() {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(UploadLogWorker.class, 15, TimeUnit.MINUTES)
                .addTag("UploadTag")
                .build();
        return request;
    }

    /**
     * 任务链
     */
    public void workChain() {
        //顺序执行任务
        WorkManager.getInstance(this).beginWith((OneTimeWorkRequest) setConstraints()).then((OneTimeWorkRequest) delayExcute()).enqueue();

        WorkContinuation workContinuation = WorkManager.getInstance(this).beginWith((OneTimeWorkRequest) setConstraints()).then((OneTimeWorkRequest) delayExcute());
        ArrayList list = new ArrayList();
        list.add(workContinuation);
        //任务链组合
        WorkContinuation.combine(list).then((OneTimeWorkRequest) setConstraints()).enqueue();


    }
}