package com.example.eva;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.eva.activity.MainDemoActivity;
import com.example.eva.service.RemindReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Notification {

    public static final String CHANNEL_ID="CHANNEL_ID";
    private String textTitle="demo";
    private String textContent="demo";
    private static boolean beginPeriod;
    private static boolean endPeriod;
    private static boolean beginFertility;
    private static boolean endFertility;
    private static boolean ovulation;
    private static boolean medicine;

    public static void getData(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        beginPeriod= sharedPreferences.getBoolean(Constant.SWITCH_BEGIN_PERIOD,true);
        endPeriod= sharedPreferences.getBoolean(Constant.SWITCH_END_PERIOD,false);
        beginFertility= sharedPreferences.getBoolean(Constant.SWITCH_BEGIN_FERTILITY,false);
        endFertility= sharedPreferences.getBoolean(Constant.SWITCH_END_FERTILITY,false);
        ovulation= sharedPreferences.getBoolean(Constant.SWITCH_OVULATION,false);
        medicine= sharedPreferences.getBoolean(Constant.SWITCH_MEDICINE,false);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void startNotification(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm");
        Calendar calendar=Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse("2020 11 20 16:27"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar currentCalendar=Calendar.getInstance();

        AlarmManager alarmManager= (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent=new Intent(context, RemindReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 1, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if(currentCalendar.after(calendar)){
            pendingIntent.cancel();
        }
    }

    public static void showNotification(Context context) {
        createNotificationChannel(context);
        Intent intent = new Intent(context, MainDemoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.pink_bird_icon)
                .setContentTitle("title")
                .setContentText("content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }
    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="eva";
            String description = "eva";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
