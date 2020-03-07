package com.chadwiedemann.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.key;
import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by chadwiedemann on 10/9/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HabitTrackerDB";
    // Books table name
    private static final String TABLE_HABITS = "habits";
    private static final String TABLE_HABIT_RESULTS = "habit_results";

    // Books Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_START_DATE = "Start_Date";
    private static final String KEY_IS_SUCCESS = "is_success";
    private static final String KEY_RESULT_DATE = "result_date";
    private static final String KEY_HABIT_ID = "habit_id";
    private static final String KEY_ALARM_HOUR = "alarm_hour";
    private static final String KEY_ALARM_MINUTE = "alarm_minute";
    private static final String KEY_ALARM_IS_ACTIVE = "alarm_is_active";

    private static final String[] HABITCOLUMNS = {KEY_ID,KEY_NAME,KEY_START_DATE,KEY_ALARM_HOUR,KEY_ALARM_MINUTE,KEY_ALARM_IS_ACTIVE};
    private static final String[] HABITRESULTCOLUMNS = {KEY_ID,KEY_IS_SUCCESS,KEY_RESULT_DATE};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_HABIT_TABLE = "CREATE TABLE habits ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, "+
                "Start_Date INTEGER, " +
                "alarm_hour INTEGER, "+
                "alarm_minute INTEGER, "+
                "alarm_is_active INTEGER, "+
                "Length INTEGER )";

        // create books table
        db.execSQL(CREATE_HABIT_TABLE);

        String CREATE_HABIT_RESULTS_TABLE = "CREATE TABLE habit_results ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "habit_id INTEGER, "+
                "result_date INTEGER, " + "is_success INTEGER )";

        // create books table
        db.execSQL(CREATE_HABIT_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS habits");
        db.execSQL("DROP TABLE IF EXISTS habit_results");

        // create fresh books table
        this.onCreate(db);
    }

    public Long addHabit(Habit habit){
        //for logging
        Log.d("addHabit", habit.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, habit.habitName); // get title
        values.put(KEY_START_DATE, habit.startDate); // get author
        values.put(KEY_ALARM_MINUTE, habit.alarmMinute);
        values.put(KEY_ALARM_HOUR, habit.alarmHour);
        values.put(KEY_ALARM_IS_ACTIVE, habit.alarmIsActive);

        // 3. insert
        long newId = db.insert(TABLE_HABITS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        db.close();
        return newId;
        // 4. close

    }

    public Long addHabitResult(HabitResult result){
        //for logging
        Log.d("addHabitResult", result.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_HABIT_ID, result.habit_id); // get habit_id
        values.put(KEY_RESULT_DATE, result.result_date); // get author
        values.put(KEY_IS_SUCCESS, 0);
        // 3. insert
        long newId = db.insert(TABLE_HABIT_RESULTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        db.close();
        return newId;
        // 4. close

    }

    public List<Habit> getAllHabits() {
        List<Habit> habits = new LinkedList<Habit>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_HABITS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Habit habit = null;
        if (cursor.moveToFirst()) {
            do {
                habit = new Habit();
                habit.id = (Integer.parseInt(cursor.getString(0)));
                habit.habitName = (cursor.getString(1));
                habit.startDate = (Integer.parseInt(cursor.getString(2)));
                habit.alarmHour = (Integer.parseInt(cursor.getString(3)));
                habit.alarmMinute = (Integer.parseInt(cursor.getString(4)));
                habit.alarmIsActive = (Integer.parseInt(cursor.getString(5)));
                habit.days = new ArrayList<HabitResult>();
                // Add book to books
                habits.add(getAllHabitResultsFor(habit));
            } while (cursor.moveToNext());
        }


        // return books
        return habits;
    }

    public Habit getAllHabitResultsFor(Habit habit) {
        List<HabitResult> results = new LinkedList<HabitResult>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_HABIT_RESULTS + " WHERE habit_id= " + String.valueOf(habit.id);

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        HabitResult result = null;
        if (cursor.moveToFirst()) {
            do {
                result = new HabitResult();
                result.id = (Integer.parseInt(cursor.getString(0)));
                result.habit_id = (Integer.parseInt(cursor.getString(1)));
                result.result_date = (Integer.parseInt(cursor.getString(2)));
                result.is_success = (Integer.parseInt(cursor.getString(3)));

                // Add book to books
                habit.days.add(result);
            } while (cursor.moveToNext());
        }

        // return books
        return habit;
    }

    public void deleteHabit(Habit book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_HABITS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(book.id) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteBook", book.toString());

    }


    public int updateHabitResult(HabitResult result) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("is_success", result.is_success); // get title


        // 3. updating row
        int i = db.update(TABLE_HABIT_RESULTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(result.id) }); //selection args
        // 4. close
        db.close();
        return i;
    }

    public int updateHabit(Habit habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ALARM_IS_ACTIVE, habit.alarmIsActive);
        values.put(KEY_ALARM_MINUTE, habit.alarmMinute);
        values.put(KEY_ALARM_HOUR, habit.alarmHour);

        int i = db.update(TABLE_HABITS,
                values,
                KEY_ID+" = ?",
                new String [] { String.valueOf(habit.id)});
        db.close();
        return i;

    }

}

