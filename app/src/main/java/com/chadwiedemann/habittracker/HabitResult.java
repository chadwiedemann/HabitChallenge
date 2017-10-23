package com.chadwiedemann.habittracker;

/**
 * Created by chadwiedemann on 10/17/17.
 */

public class HabitResult {

    int habit_id;
    int id;
    int is_success;
    int result_date;

    public HabitResult(){}

    public HabitResult(int habit_id, int date) {
        super();
        this.habit_id = habit_id;
        this.result_date = date;
    }

}
