package com.chadwiedemann.habittracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static com.chadwiedemann.habittracker.DataHolder.alarmManager;


/**
 * Created by chadwiedemann on 3/1/18.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getExtras();
        int hour = intent.getExtras().getInt("hour");
        int minute = intent.getExtras().getInt("minute");
        int habitID = intent.getExtras().getInt("habitID");
        Habit tempHabit = new Habit();
        tempHabit.alarmHour = hour;
        tempHabit.alarmMinute = minute;
        tempHabit.id = habitID;
        setAlarm(context, tempHabit);
        Intent startIntent = new Intent(context, RingtonePlayingService.class);
        context.startService(startIntent);
        //start activity
        Intent i = new Intent();
        i.setClassName("com.chadwiedemann.habittracker", "com.chadwiedemann.habittracker.HomeScreenActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("isStartingFromAlarm", "true");
        context.startActivity(i);
    }

   static public void setAlarm(Context context, Habit habit) {
       AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
       Intent myIntent = new Intent(context, AlarmReceiver.class);



       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.HOUR_OF_DAY, habit.alarmHour);
       calendar.set(Calendar.MINUTE, habit.alarmMinute);

       Calendar testCalender = Calendar.getInstance();
       testCalender.add(Calendar.MINUTE, 2);
       if(calendar.before(testCalender)) {
            calendar.add(Calendar.DATE, 1);
       }

       myIntent.putExtra("hour", habit.alarmHour);
       myIntent.putExtra("minute", habit.alarmMinute);
       myIntent.putExtra("habitID", habit.id);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(context, habit.id, myIntent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
           alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent), pendingIntent);
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("chadTag", "Alarm set for habit " + habit.habitName);
        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    static public void cancelAlarm(Context context, Habit habit) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, habit.id, myIntent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.d("chadTag", "Alarm canceled");
        }
        Intent stopIntent = new Intent(context, RingtonePlayingService.class);
        context.stopService(stopIntent);
    }

}
