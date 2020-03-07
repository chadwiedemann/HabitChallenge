package com.chadwiedemann.habittracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_about_us);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
