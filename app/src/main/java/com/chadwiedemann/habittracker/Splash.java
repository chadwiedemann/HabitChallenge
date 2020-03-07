package com.chadwiedemann.habittracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by chadwiedemann on 2/22/18.
 */

public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
//        getWindow().setBackgroundDrawableResource(R.drawable.splachscreen);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, HomeScreenActivity.class));
                finish();
            }
        }, secondsDelayed * 2000);
    }

}
