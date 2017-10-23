package com.chadwiedemann.habittracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class DisplayHabitActivity extends AppCompatActivity {

    MySQLiteHelper db;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySQLiteHelper(this);

        setContentView(R.layout.activity_display_habit);
        TextView currentHabitTextView =  (TextView) findViewById(R.id.habitTextView);
        Habit habitToDisplay = DataHolder.getInstance().getDisplayedHabit();
        currentHabitTextView.setText(habitToDisplay.habitName);

        mListView = (ListView) findViewById(R.id.current_Habit_list_view);
// 1
        final List<HabitResult> currentHabitResults = habitToDisplay.days;
// 2
//        String[] listItems = new String[currentHabitResults.size()];
//// 3
//        for(int i = 0; i < currentHabitResults.size(); i++){
//            HabitResult result = currentHabitResults.get(i);
//
//            listItems[i] = Integer.toString(result.result_date);
//        }
// 4
        HabitResultAdapter adapter = new HabitResultAdapter(this, R.layout.habit_result_row, currentHabitResults);
        mListView.setAdapter(adapter);
    }

    public void deleteHabitClicked(View view) {
        db.deleteHabit(DataHolder.getInstance().getDisplayedHabit());
        this.finish();

    }

    public class HabitResultAdapter extends ArrayAdapter{

        private List<HabitResult> resultsList;
        private int resource;
        private LayoutInflater inflater;
        int selectedPosition = 0;


        public HabitResultAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<HabitResult> objects) {
            super(context, resource, objects);
            resultsList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView dateText;
            final RadioButton successButton;
            final RadioButton failButton;
            RadioGroup buttonGroup;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.habit_result_row, null);
            }
            final HabitResult changingResult = resultsList.get(position);
            successButton = (RadioButton) convertView.findViewById(R.id.successRadioButton);
            failButton = (RadioButton) convertView.findViewById(R.id.failRadioButton);
            buttonGroup = (RadioGroup) convertView.findViewById(R.id.resultRadioGroup);
            buttonGroup.clearCheck();


            successButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    failButton.setChecked(false);
                    changingResult.is_success = 2;
                    db.updateHabitResult(changingResult);
                    notifyDataSetChanged();
                }
            });
            failButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successButton.setChecked(false);
                    changingResult.is_success = 1;
                    db.updateHabitResult(changingResult);
                    notifyDataSetChanged();
                }
            });

            switch (resultsList.get(position).is_success) {
                case 1:
                    successButton.setChecked(false);
                    failButton.setChecked(true);
                    break;
                case 2:
                    successButton.setChecked(true);
                    failButton.setChecked(false);
                    break;
                default:
                    successButton.setChecked(false);
                    failButton.setChecked(false);
            }


            dateText = (TextView) convertView.findViewById(R.id.habitResultDateTextView);
            Date resultDate = AppDataChanger.IntegerToDate(resultsList.get(position).result_date);
            SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd-yyyy");
            String newstring = dt1.format(resultDate);
            dateText.setText(newstring);
//            buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                    switch (checkedId) {
//                        case R.id.failRadioButton:
//                            changingResult.is_success = 1;
//                            db.updateHabitResult(changingResult);
//
//                            break;
//                        case R.id.successRadioButton:
//                            changingResult.is_success = 2;
//                            db.updateHabitResult(changingResult);
//                            break;
//                        default:
//
//                    }
//                    notifyDataSetChanged();
//                }
//
//            });
            return convertView;
        }
    }

}