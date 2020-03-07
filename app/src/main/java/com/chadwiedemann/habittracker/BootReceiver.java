package com.chadwiedemann.habittracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.chadwiedemann.habittracker.R.id.alarmSwitch;

/**
 * Created by chadwiedemann on 3/8/18.
 */

public class BootReceiver extends BroadcastReceiver {

    private MyPreferences preferences;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int timeHour;
    int timeMinute;
    MySQLiteHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = new MySQLiteHelper(context);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            resetAllAlarms(context);
            preferences = MyPreferences.getInstance(context);
            if (preferences.getStringData("alarmState").equals("on")){
                // Set the alarm here
                timeHour = preferences.getIntData("hourTime");
                timeMinute = preferences.getIntData("minuteTime");
                alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                Intent myIntent = new Intent(context, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
                setAlarm();
            }else{

            }

        }
    }

    private void setAlarm(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        Log.d("chadTag", String.valueOf(timeHour));
        calendar.set(Calendar.MINUTE, timeMinute);
        Log.d("chadTag", String.valueOf(timeMinute));
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 24*60*60*1000, pendingIntent);
        }
    }

    private void resetAllAlarms(Context context){
        final List<Habit> allHabits = db.getAllHabits();
        for(int i = 0; i < allHabits.size(); i++){
            Habit habit = allHabits.get(i);
            if (habit.alarmIsActive > 0){
                AlarmReceiver.setAlarm(context, habit);
            }
        }
    }
}
