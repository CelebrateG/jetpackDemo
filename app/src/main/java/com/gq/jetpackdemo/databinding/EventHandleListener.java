package com.gq.jetpackdemo.databinding;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 点击事件类
 */
public class EventHandleListener {

    private Context context;

    public EventHandleListener(Context context) {
        this.context = context;
    }

    public void onButtonClicked(View view) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
    }
}
