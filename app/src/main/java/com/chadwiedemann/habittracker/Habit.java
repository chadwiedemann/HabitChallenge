package com.chadwiedemann.habittracker;

import java.util.ArrayList;
import java.util.Date;

import static android.R.attr.author;
import static android.R.attr.id;
import static android.os.Build.ID;

/**
 * Created by chadwiedemann on 10/4/17.
 */

public class Habit {

    String habitName;
    int startDate;
    int id;
    int habitLength;
    ArrayList<HabitResult> days;

    public Habit(){}

    public Habit(String name, int startDate, int habitLength) {
        super();
        this.habitName = name;
        this.startDate = startDate;
        this.habitLength = habitLength;
        this.days = new ArrayList<HabitResult>();
    }


    //getters & setters

    @Override
    public String toString() {
        return "Habit [id=" + id + ", name=" + habitName + ", startDate=" + startDate
                + "]";
    }
}
