package com.example.eva.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.eva.Caculator;
import com.example.eva.R;
import com.example.eva.activity.MainActivity;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.example.eva.model.Remind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class Notification {

    public static final String CHANNEL_ID = "CHANNEL_ID";

    public static final String ACTION_BEGIN_PERIOD = "ACTION BEGIN PERIOD";
    public static final String ACTION_END_PERIOD = "ACTION END PERIOD";
    public static final String ACTION_BEGIN_FERTILITY = "ACTION BEGIN FERTILITY";
    public static final String ACTION_END_FERTILITY = "ACTION END FERTILITY";
    public static final String ACTION_OVULATION = "ACTION OVULATION";
    public static final String ACTION_MEDICINE = "ACTION MEDICINE";

    public static final String ACTION_DAILY="ACTION DAILY";


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void startNotification(Context context, DBManager db) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        CyclePeriod cyclePeriod=db.getCurrentCycle(1);
        List<Remind> reminds=db.getListRemindPeriodic();
        Remind remindMedicine=db.getRemindMedicine();

        createDailyNotification(context,alarmManager);

        if(reminds.get(0).isChooseSwitch()){
            String time= Caculator.getDate(cyclePeriod.getNextBeginCycle(), -2)+" "+
                    reminds.get(0).getTime();
            createNotification(context, alarmManager,time,ACTION_BEGIN_PERIOD,reminds.get(0).getID());
        }
        if(reminds.get(1).isChooseSwitch() & cyclePeriod.getUserEndDate()!=null){
            String time= Caculator.getDate(cyclePeriod.getUserEndDate(), 0)+" "+
                    reminds.get(1).getTime();
            createNotification(context, alarmManager,time,ACTION_END_PERIOD,reminds.get(1).getID());
        }
        if(reminds.get(2).isChooseSwitch()){
            String time= Caculator.getDate(cyclePeriod.getBeginFertility(), -1)+" "+
                    reminds.get(2).getTime();
            createNotification(context, alarmManager,time,ACTION_BEGIN_FERTILITY,reminds.get(2).getID());
        }
        if(reminds.get(3).isChooseSwitch()){
            String time= Caculator.getDate(cyclePeriod.getEndFertility(), 0)+" "+
                    reminds.get(3).getTime();
            createNotification(context, alarmManager,time,ACTION_END_FERTILITY,reminds.get(3).getID());
        }
        if(reminds.get(4).isChooseSwitch()){
            String time= Caculator.getDate(cyclePeriod.getOvulationDate(), 0)+" "+
                    reminds.get(4).getTime();
            createNotification(context, alarmManager,time,ACTION_MEDICINE,reminds.get(4).getID());
        }
        if(remindMedicine.isChooseSwitch()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            int state=Caculator.getStage(db.getCurrentCycle(1));
            if(state==Caculator.IN_PERIOD){
                Calendar calendar = Calendar.getInstance();
                String[] times=remindMedicine.getTime().split(",");
                int numberTime=times.length;
                switch (numberTime){
                    case 1:
                        String time= dateFormat.format(calendar.getTime())+" "+times[0];
                        createNotification(context, alarmManager,time,ACTION_MEDICINE,remindMedicine.getID());
                        break;
                    case 2:
                        String time1= dateFormat.format(calendar.getTime())+" "+times[0];
                        createNotification(context, alarmManager,time1,ACTION_MEDICINE,remindMedicine.getID());
                        String time2= dateFormat.format(calendar.getTime())+" "+times[1];
                        createNotification(context, alarmManager,time2,ACTION_MEDICINE,remindMedicine.getID()+1);
                        break;
                    case 3:
                        String time3= dateFormat.format(calendar.getTime())+" "+times[0];
                        createNotification(context, alarmManager,time3,ACTION_MEDICINE,remindMedicine.getID());
                        String time4= dateFormat.format(calendar.getTime())+" "+times[1];
                        createNotification(context, alarmManager,time4,ACTION_MEDICINE,remindMedicine.getID()+1);
                        String time5= dateFormat.format(calendar.getTime())+" "+times[2];
                        createNotification(context, alarmManager,time5,ACTION_MEDICINE,remindMedicine.getID()+2);
                        break;
                }
            }
        }

    }
    private static void createNotification(Context context, AlarmManager alarmManager, String time, String action,
                                           int requestCode) {
        Calendar currentCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, RemindReceiver.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (currentCalendar.after(calendar)) {
            Log.d("BacNT", "cancel");
            pendingIntent.cancel();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
    private static void createDailyNotification(Context context, AlarmManager alarmManager){

        Intent alarmIntent = new Intent(context , RemindReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setAction(ACTION_DAILY);
        alarmManager.cancel(pendingIntent);

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        alarmStartTime.set(Calendar.HOUR_OF_DAY, 20);
        alarmStartTime.set(Calendar.MINUTE, 00);

        if (now.after(alarmStartTime)) {
            Log.d("Hey","Added a day");
            alarmStartTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void showNotification(Context context, String title, String content) {
        createNotificationChannel(context);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.pink_bird_icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "eva";
            String description = "eva";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
