package com.gq.jetpackdemo.lifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.Navigation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gq.jetpackdemo.R;
import com.gq.jetpackdemo.livedata.fragment.LiveDataFragmentActivity;
import com.gq.jetpackdemo.navigation.NavigationActivity;
import com.gq.jetpackdemo.room.RoomDemoActivity;
import com.gq.jetpackdemo.viewmodel.TimerActivity;
import com.gq.jetpackdemo.workmamager.WorkManagerActivity;

public class MainActivity extends AppCompatActivity {
    private MyLocationListener locationListener;
    private String TAG = "MainActivity";
    private Button startBtn, stopBtn, navigationBtn, viewModelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.startService);
        stopBtn = findViewById(R.id.stopService);
        navigationBtn = findViewById(R.id.navigationBtn);
        viewModelBtn = findViewById(R.id.viewModel);
        findViewById(R.id.viewModelLiveDataFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LiveDataFragmentActivity.class);
                startActivity(intent);
            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);
            }
        });
        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });
        viewModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        locationListener = new MyLocationListener(new MyLocationListener.OnLocationChangedListener() {
            @Override
            public void onChanged(double latitude, double longitude) {
                Log.d(TAG, "onChanged: ");
            }
        });
        findViewById(R.id.roomlLiveDataViewModel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoomDemoActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.workManagerDataBinding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WorkManagerActivity.class);
                startActivity(intent);
            }
        });

        //绑定
        getLifecycle().addObserver(locationListener);

    }

    private int notifyId = 0;
    private final String CHANNEL_ID = "10";



    /**
     * 以下为 DeepLinkDemo
     * 模拟发送通知
     */
    public void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channelName", importance);
            channel.setDescription("descripition");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("DeepLinkDemo")
                .setContentText("Hello world")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notifyId, builder.build());
//        notifyId++;
    }

    private PendingIntent getPendingIntent() {
        Bundle bundle = new Bundle();
        bundle.putString("param", "this is Notification");
        return Navigation.findNavController(this, R.id.sendNotificationBtn)
                .createDeepLink()
                .setGraph(R.navigation.graph_deeplink_activity)
//                .setDestination(R.id.dee)
                .setArguments(bundle)
                .createPendingIntent();

    }

}