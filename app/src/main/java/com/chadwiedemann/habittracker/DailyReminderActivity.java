package com.chadwiedemann.habittracker;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.Calendar;
import android.app.AlarmManager;
import android.widget.Switch;
import android.widget.TimePicker;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DailyReminderActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int timeHour;
    private int timeMinute;
    private TimePicker timePicker;
    private MyPreferences preferences;
    private Switch alarmSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_daily_reminder);
        preferences = MyPreferences.getInstance(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);
        if (preferences.getStringData("alarmState").equals("on")){
            alarmSwitch.setChecked(true);
        }else{
            alarmSwitch.setChecked(false);
        }
        timePicker.setCurrentHour(preferences.getIntData("hourTime"));
        timePicker.setCurrentMinute(preferences.getIntData("minuteTime"));
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(DailyReminderActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(DailyReminderActivity.this, 0, myIntent, 0);

        timePicker.setOnTimeChangedListener(this);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void createReminderPressed(View view) {
        Intent myIntent = new Intent(this, AlarmClockActivity.class);
        startActivity(myIntent);
    }

    public void alarmSwitchPressed(View view) {
        Switch s = (Switch) findViewById(R.id.alarmSwitch);

        if (s.isChecked()){

            setAlarm();
            preferences.saveStringData("alarmState","on");
        }else {
            cancelAlarm();
            preferences.saveStringData("alarmState","off");
        }

    }

    private void setAlarm(){

        preferences.saveIntData("hourTime", timeHour);
        preferences.saveIntData("minuteTime", timeMinute);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        Log.d("chadTag", String.valueOf(timeHour));
        calendar.set(Calendar.MINUTE, timeMinute);
        Log.d("chadTag", String.valueOf(timeMinute));
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        Habit tempHabit = new Habit();
        tempHabit.alarmMinute = timeMinute;
        tempHabit.alarmHour = timeHour;
        tempHabit.id = 99999;
        AlarmReceiver.setAlarm(this, tempHabit);

    }
    private void cancelAlarm() {
        Habit tempHabit = new Habit();
        tempHabit.alarmMinute = timeMinute;
        tempHabit.alarmHour = timeHour;
        tempHabit.id = 99999;
        AlarmReceiver.cancelAlarm(this, tempHabit);
        Intent stopIntent = new Intent(this, RingtonePlayingService.class);
        this.stopService(stopIntent);
    }


    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        timeMinute = timePicker.getCurrentMinute();
        timeHour = timePicker.getCurrentHour();
        if (alarmSwitch.isChecked()){
            setAlarm();
        }
    }
}
