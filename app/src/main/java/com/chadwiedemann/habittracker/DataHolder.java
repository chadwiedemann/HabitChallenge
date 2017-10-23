package com.chadwiedemann.habittracker;

import android.app.AlarmManager;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * Created by chadwiedemann on 10/5/17.
 */

public class DataHolder {

    private Habit displayedHabit;
    private ArrayList habitArray = new ArrayList();
    public static MediaPlayer mediaPlayer;
    public static AlarmManager alarmManager;

    public Habit getDisplayedHabit() {return displayedHabit;}
    public void setHabit(Habit habit) {this.displayedHabit = habit;}

    public  ArrayList getHabitArray() {return  habitArray;}
    public void setHabitArray(ArrayList array) {this.habitArray = array;}

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}


}
