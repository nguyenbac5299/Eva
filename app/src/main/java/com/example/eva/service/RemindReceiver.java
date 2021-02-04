package com.example.eva.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.eva.R;
import com.example.eva.data.DBManager;
import com.example.eva.model.Remind;

public class RemindReceiver extends BroadcastReceiver {
    DBManager dbManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BacNT", "đã vào receiver rồi nè!");
        dbManager = new DBManager(context);
        if(intent==null|| intent.getAction()==null){
            Notification.showNotification(context,context.getResources().getString(R.string.daily_title),context.getResources().getString(R.string.daily_remind_content) );
        }
        else if (intent.getAction().equals(Notification.ACTION_BEGIN_PERIOD)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.start_period_title),
                    dbManager.getRemind(0).getContent());
        } else if (intent.getAction().equals(Notification.ACTION_END_PERIOD)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.end_period_title),
                    dbManager.getRemind(1).getContent());
        } else if (intent.getAction().equals(Notification.ACTION_BEGIN_FERTILITY)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.start_fertility_title),
                    dbManager.getRemind(2).getContent());
        } else if (intent.getAction().equals(Notification.ACTION_END_FERTILITY)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.end_fertility_title),
                    dbManager.getRemind(3).getContent());
        } else if (intent.getAction().equals(Notification.ACTION_OVULATION)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.ovulation),
                    dbManager.getRemind(4).getContent());
        } else if (intent.getAction().equals(Notification.ACTION_MEDICINE)) {
            Notification.showNotification(context,
                    context.getResources().getString(R.string.medicine_title),
                    dbManager.getRemind(5).getContent());
        }
    }

}
