package com.gq.jetpackdemo.workmamager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 1、针对不需要及时完成的任务
 * 2、保证任务一定会被执行
 * Worker定义任务
 */
public class UploadLogWorker extends Worker {

    public UploadLogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("UploadLogWorker", "doWork()");

        //接受外面传递进来的数据
        String inputData = getInputData().getString("input_data");

        //任务执行完成之后返回数据
        Data outputData = new Data.Builder().putString("output_data","task success").build();

        return Result.success(outputData);
    }

}
