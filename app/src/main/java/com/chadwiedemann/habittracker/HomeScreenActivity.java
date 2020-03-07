package com.chadwiedemann.habittracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.Console;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);


        Intent intent = getIntent();
        if (intent != null){
            if (intent.getExtras() != null) {
                String isAlarmStart = intent.getExtras().getString("isStartingFromAlarm");
                if (isAlarmStart.equals("true")){
                    AlertDialog.Builder alertDialog2;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        alertDialog2 = new AlertDialog.Builder(HomeScreenActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        alertDialog2 = new AlertDialog.Builder(HomeScreenActivity.this);
                    }


                    alertDialog2.setTitle("Stop Alarm");

                    alertDialog2.setMessage("Time to check in to record the progress on your daily habit.");

                    alertDialog2.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                    Intent stopIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
                                    getApplicationContext().stopService(stopIntent);
                                    Intent progressToolIntent = new Intent(getApplicationContext(), HabitListActivity.class);
                                    startActivity(progressToolIntent);
                                }
                            });
                    alertDialog2.show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.daily_reminder_item:
                Intent myIntent = new Intent(this, DailyReminderActivity.class);
                startActivity(myIntent);
                return true;
            case R.id.about_the_app_item:
                Intent myIntent1 = new Intent(this, AboutUsActivity.class);
                startActivity(myIntent1);
                return true;
            case R.id.products_item:
                Intent myIntent2 = new Intent(this, ProductsActivity.class);
                startActivity(myIntent2);
                return true;
            case R.id.support_mission_item:
                Intent myIntent3 = new Intent(this, SupportOurMissionActivity.class);
                startActivity(myIntent3);
                return true;
            case R.id.contact_us_item:
                Intent myIntent4 = new Intent(this, ContactUsActivity.class);
                startActivity(myIntent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void gotoRecordProgressActivity(View view) {
        Intent progressToolIntent = new Intent(this, HabitListActivity.class);
        startActivity(progressToolIntent);
    }

    public void goToCravingMeterActivity(View view){
        Intent progressToolIntent = new Intent(this, CravingMeterActivity.class);
        startActivity(progressToolIntent);
    }

    public void goToManageYourTriggersActivity(View view){
        Intent manageYourTriggersIntent = new Intent(this, ManageYourTriggersActivity.class);
        startActivity(manageYourTriggersIntent);
    }



    public void goToWebView(View view) {
        String websiteURL = "";
        switch(view.getId()) {
            case R.id.get_started_button:
                websiteURL = "http://www.empowerministry.org/app/get-started-now/";
                break;
            case R.id.app_handbook_button:
                websiteURL = "http://www.empowerministry.org/app/handbook/";
                break;
            case R.id.craving_meter_button:
                websiteURL = "https://www.empowerministry.org/";
                break;
            case R.id.warning_signs_checklist_button:
                websiteURL = "https://www.empowerministry.org/app/craving-toolbox/craving-warning-signs/";
                break;
            case R.id.craving_toolbox_button:
                websiteURL = "https://www.empowerministry.org/app/craving-toolbox/";
                break;
            case R.id.spiritual_toolbox_button:
                websiteURL = "https://www.empowerministry.org/app/spiritual-help-tool-box/";
                break;
            case R.id.five_steps_to_overcoming_craving_button:
                websiteURL = "https://www.empowerministry.org/app/craving-toolbox/5-steps-to-stop-a-craving/";
                break;
            case R.id.triggers_checklist_button:
                websiteURL = "https://www.empowerministry.org/app/craving-toolbox/managing-triggers-checklist/";
                break;
        }
        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra("linkURL", websiteURL);
        startActivity(webViewIntent);
    }
}
