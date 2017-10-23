package com.chadwiedemann.habittracker;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.list;
import static android.media.CamcorderProfile.get;

public class HabitListActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextView;
    Habit newHabit = new Habit();
    MySQLiteHelper db;

    @Override
    protected void onResume() {
        super.onResume();
        mListView = (ListView) findViewById(R.id.allHabit_list_view);
// 1
        final List<Habit> allHabits = db.getAllHabits();
// 2
        String[] listItems = new String[allHabits.size()];
// 3
        for(int i = 0; i < allHabits.size(); i++){
            Habit habit = allHabits.get(i);

            listItems[i] = habit.habitName;
        }
// 4
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Habit listItem = allHabits.get(position);


                DataHolder.getInstance().setHabit(listItem);
                Intent i = new Intent(HabitListActivity.this, DisplayHabitActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);
        db = new MySQLiteHelper(this);

    }


    public void gotoCreateNewHabitScreen(View view) {

        Intent createHabitActivity = new Intent(this, CreateNewHabitActivity.class);
        startActivity(createHabitActivity);

    }


}