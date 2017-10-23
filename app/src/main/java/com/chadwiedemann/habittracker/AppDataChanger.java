package com.chadwiedemann.habittracker;

import android.util.Log;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

import static android.R.attr.value;

/**
 * Created by chadwiedemann on 10/17/17.
 */

public class AppDataChanger {

    public static Date IntegerToDate(int intFromSQL){
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(Integer.toString(intFromSQL));
        } catch (ParseException e) {

        }

        return date;
    }

    public static int DateToInteger(Date date){
        //Initialize your Date however you like it.

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = (calendar.get(Calendar.YEAR)) * 10000;
        //Add one to month {0 - 11}
        int month = (calendar.get(Calendar.MONTH) + 1) * 100;
        int day = (calendar.get(Calendar.DAY_OF_MONTH));

        return year + month + day;
    }

}
