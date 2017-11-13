package com.chadwiedemann.habittracker;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.media.CamcorderProfile.get;

public class DisplayHabitActivity extends AppCompatActivity {

    MySQLiteHelper db;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_display_habit);
        TextView currentHabitTextView =  (TextView) findViewById(R.id.habitTextView);
        Habit habitToDisplay = DataHolder.getInstance().getDisplayedHabit();
        currentHabitTextView.setText(habitToDisplay.habitName);

        mListView = (ListView) findViewById(R.id.current_Habit_list_view);
        final List<HabitResult> currentHabitResults = habitToDisplay.days;

        HabitResultAdapter adapter = new HabitResultAdapter(this, R.layout.habit_result_row, currentHabitResults);
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void deleteHabitClicked(View view) {
        db.deleteHabit(DataHolder.getInstance().getDisplayedHabit());
        this.finish();

    }

    public class HabitResultAdapter extends ArrayAdapter{

        private List<HabitResult> resultsList;
        private int resource;
        private LayoutInflater inflater;
        int selectedPosition = 0;


        public HabitResultAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HabitResult> objects) {
            super(context, resource, objects);
            resultsList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView dateText;
            final RadioButton successButton;
            final RadioButton failButton;
            RadioGroup buttonGroup;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.habit_result_row, null);
            }
            final HabitResult changingResult = resultsList.get(position);
            successButton = (RadioButton) convertView.findViewById(R.id.successRadioButton);
            failButton = (RadioButton) convertView.findViewById(R.id.failRadioButton);
            buttonGroup = (RadioGroup) convertView.findViewById(R.id.resultRadioGroup);
            buttonGroup.clearCheck();


            successButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date firstDateOfHabit = AppDataChanger.IntegerToDate(resultsList.get(0).result_date) ;
                    int firstDay = AppDataChanger.IntegerDatePlusDay(0, firstDateOfHabit);
                    int thirdDay = AppDataChanger.IntegerDatePlusDay(2, firstDateOfHabit);
                    int seventhDay = AppDataChanger.IntegerDatePlusDay(6, firstDateOfHabit);
                    int tenthDay = AppDataChanger.IntegerDatePlusDay(9, firstDateOfHabit);
                    int fourteenthDay = AppDataChanger.IntegerDatePlusDay(13, firstDateOfHabit);
                    int twentyFirstDay = AppDataChanger.IntegerDatePlusDay(20, firstDateOfHabit);
                    int twentyEightDay = AppDataChanger.IntegerDatePlusDay(27, firstDateOfHabit);
                    int thirtyFifthDay = AppDataChanger.IntegerDatePlusDay(34, firstDateOfHabit);
                    int fourtySecondDay = AppDataChanger.IntegerDatePlusDay(41, firstDateOfHabit);
                    int fiftiethDay = AppDataChanger.IntegerDatePlusDay(49, firstDateOfHabit);
                    int fiftyFifthDay = AppDataChanger.IntegerDatePlusDay(54, firstDateOfHabit);
                    int sixtiethDay = AppDataChanger.IntegerDatePlusDay(59, firstDateOfHabit);
                    if (changingResult.result_date == firstDay) {
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Congratulations! Making your first day is the hardest. Go ahead and shoot for day 3.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == thirdDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Great job! You are creating a new habit and breaking an old one. Don’t give up. You can make it to day 7.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == seventhDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Way to go! One week is a wonderful accomplishment. You are creating new neural pathways for your new behavior. Set your sights on day 10.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == tenthDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "10 Days is a significant milestone. You are doing great! Don’t get discouraged or weary 2 weeks is right around the corner.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == fourteenthDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Sensational! The fact that you are checking this day off shows how serious you are. Don’t be discouraged by any set-backs. You are creating new habits and breaking old ones. Let’s go for 21 days!", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == twentyFirstDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Excellent! Studies have shown that many new habits are formed in 21 days. Your brain is being rewired and your behavior is being changed. One week left to 28 days!", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == twentyEightDay){
                        for (int i=0; i < 5; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Tremendous! Congratulations! You have made it to 28 days. Celebrate this!!! Tell someone. This has been proven to be a significant milestone in creating new habits. To really make this stick and not to let your guard down you need to move into relapse prevention mode. Manage your triggers, keep accountable to someone, use your support group. Your next target is day 35.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == thirtyFifthDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "You are doing fantastic. You new way of life is becoming your new normal. Next goal is day 42.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == fourtySecondDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Marvelous! Keep it up. If you are struggling at all get some help. Don’t give up.\n" +
                                    "The next goal is day 50.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == fiftiethDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "You are doing great! Celebrate. Let someone know how you are doing. Also, don’t let down your guard. Keep doing the things that got you this far. The next target is Day 55.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == fiftyFifthDay){
                        for (int i=0; i < 3; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Outstanding! You are almost at 60 days. You have done a great job. Keep it up. The next goal is day 60.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    if (changingResult.result_date == sixtiethDay){
                        for (int i=0; i < 6; i++) {
                            Toast.makeText(getApplication().getApplicationContext(), "Congratulations you have completed the 60 day Habit Challenge! Your habit changes are deeply planted in your brain. Continue to do so well. If you begin to get lax, if stress is causing you to become tempted, or a slip gets you down, come back and do another 28-day challenge. The discipline of monitoring will help you reestablish your habit.", Toast.LENGTH_LONG).show();
                        }
                        MediaPlayer mPlayer2;
                        mPlayer2= MediaPlayer.create(getApplication().getApplicationContext(), R.raw.clapping);
                        mPlayer2.start();
                    }
                    failButton.setChecked(false);
                    changingResult.is_success = 2;
                    db.updateHabitResult(changingResult);
                    notifyDataSetChanged();
                }
            });
            failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successButton.setChecked(false);
                    changingResult.is_success = 1;
                    db.updateHabitResult(changingResult);
                    notifyDataSetChanged();
                }
            });

            switch (resultsList.get(position).is_success) {
                case 1:
                    successButton.setChecked(false);
                    failButton.setChecked(true);
                    break;
                case 2:
                    successButton.setChecked(true);
                    failButton.setChecked(false);
                    break;
                default:
                    successButton.setChecked(false);
                    failButton.setChecked(false);
            }


            dateText = (TextView) convertView.findViewById(R.id.habitResultDateTextView);
            Date resultDate = AppDataChanger.IntegerToDate(resultsList.get(position).result_date);
            SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd-yyyy");
            String newstring = dt1.format(resultDate);
            dateText.setText(newstring);
            return convertView;
        }
    }

}