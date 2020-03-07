package com.chadwiedemann.habittracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chadwiedemann on 3/7/18.
 */

public class MyPreferences {
    private static MyPreferences myPreferences;
    private SharedPreferences sharedPreferences;

    public static MyPreferences getInstance(Context context) {
        if (myPreferences == null) {
            myPreferences = new MyPreferences(context);
        }
        return myPreferences;
    }

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("MyCustomNamedPreference",Context.MODE_PRIVATE);
    }

    public void saveStringData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public String getStringData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }

    public void saveIntData(String key,int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putInt(key, value);
        prefsEditor.commit();
    }

    public int getIntData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }



}
