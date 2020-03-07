package com.chadwiedemann.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SupportOurMissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_support_our_mission);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
