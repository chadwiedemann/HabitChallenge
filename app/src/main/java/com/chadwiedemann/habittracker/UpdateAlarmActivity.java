package com.chadwiedemann.habittracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.chadwiedemann.habittracker.DataHolder.alarmManager;

public class UpdateAlarmActivity extends AppCompatActivity {

    Habit selectedHabit;
    MySQLiteHelper db;
    TimePicker timePicker;
    Switch mySwitch;
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_alarm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectedHabit = DataHolder.getInstance().getDisplayedHabit();
        mySwitch = (Switch) findViewById(R.id.alarmSwitch3);
        timePicker = (TimePicker) findViewById(R.id.timePicker3);
        if (selectedHabit.alarmIsActive == 0){
            mySwitch.setChecked(false);
        }else{
            mySwitch.setChecked(true);
        }
        timePicker.setCurrentHour(selectedHabit.alarmHour);
        timePicker.setCurrentMinute(selectedHabit.alarmMinute);
        db = new MySQLiteHelper(this);

    }

    @Override
    public boolean onSupportNavigateUp(){
        saveAlarm();
        finish();
        return true;
    }

    public void saveAlarmClicked(View view) {
        saveAlarm();
        finish();
    }
    private void saveAlarm() {
        selectedHabit.alarmMinute = timePicker.getCurrentMinute();
        selectedHabit.alarmHour = timePicker.getCurrentHour();

        if (mySwitch.isChecked()){
            selectedHabit.alarmIsActive = 1;
            AlarmReceiver.setAlarm(this, selectedHabit);
        }else {
            selectedHabit.alarmIsActive = 0;
            AlarmReceiver.cancelAlarm(this, selectedHabit);
        }
        db.updateHabit(selectedHabit);
    }


}
