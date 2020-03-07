package com.chadwiedemann.habittracker;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ContentValues;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.chadwiedemann.habittracker.DataHolder.alarmManager;


public class CreateNewHabitActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MySQLiteHelper db;
    private Switch alarmSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_habit);
        db = new MySQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.habitLengthSpinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Choose Habit Length");
        categories.add("10 Day Habit");
        categories.add("21 Day Habit");
        categories.add("28 Day Habit");
        categories.add("60 Day Habit");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        EditText editText = (EditText) findViewById(R.id.habitNameText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void cancelNewHabitClicked(View view) {

        this.finish();

    }



    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void saveNewHabitClicked(View view) {
        EditText textView = (EditText) findViewById(R.id.habitNameText);
        Switch alarmSwitch = (Switch) findViewById(R.id.alarmSwitch2);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker2);
        int alarmState = 0;
        if (alarmSwitch.isChecked()){
            alarmState = 1;
        }

        Habit newHabit = new Habit();
        newHabit.habitName = textView.getText().toString();
        newHabit.startDate = AppDataChanger.DateToInteger(new Date());
        newHabit.alarmIsActive = alarmState;
        newHabit.alarmMinute = timePicker.getCurrentMinute();
        newHabit.alarmHour = timePicker.getCurrentHour();
        Spinner spinner = (Spinner) findViewById(R.id.habitLengthSpinner);

        switch ((String)spinner.getSelectedItem()) {
            case "Choose Habit Length":
                newHabit.habitLength = 0;
                break;
            case "10 Day Habit":
                newHabit.habitLength = 10;

                break;
            case "21 Day Habit":
                newHabit.habitLength = 21;
                break;
            case "28 Day Habit":
                newHabit.habitLength = 28;
                break;
            default:
                newHabit.habitLength = 60;
                break;
        }


        if (newHabit.habitLength != 0) {
            Long newId = db.addHabit(newHabit);
            for (int x = 0; x < newHabit.habitLength; x++){
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DATE, x);  // number of days to add
                Date newDate = c.getTime();  // dt is now the new date
                HabitResult newHabitResult = new HabitResult(newId.intValue() , AppDataChanger.DateToInteger(newDate));
                Long newResutlid = db.addHabitResult(newHabitResult);
            }
            newHabit.id = newId.intValue();
            if (alarmSwitch.isChecked()){
                AlarmReceiver.setAlarm(this, newHabit);
            }
            this.finish();
        }else{
            Toast.makeText(this.getApplication().getApplicationContext(), "Please select desired length before saving Habit", Toast.LENGTH_SHORT).show();
        }


    }
}
