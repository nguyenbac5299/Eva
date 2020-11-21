package com.example.eva.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.eva.Notification;
import com.example.eva.R;
import com.example.eva.activity.MainDemoActivity;

public class RemindReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BacNT","đã vào receiver rồi nè!");
        Notification.showNotification(context);
    }

}
