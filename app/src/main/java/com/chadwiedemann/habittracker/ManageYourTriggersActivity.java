package com.chadwiedemann.habittracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageYourTriggersActivity extends AppCompatActivity {

    ArrayList triggers = new ArrayList();
    private ListView mListView;
    Trigger selectedTrigger;
    int selectedTriggerIndex;
    private MyPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_your_triggers);
        mListView = (ListView) findViewById(R.id.triggers_list_view);
        loadTriggers();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String[] listItems = new String[triggers.size()];
        for(int i = 0; i < triggers.size(); i++){
            Trigger trigger = (Trigger) triggers.get(i);

            listItems[i] = trigger.name;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTriggerIndex = position;
                selectedTrigger = (Trigger) triggers.get(position);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void addTriggerClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Trigger Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                triggers.add(new Trigger(input.getText().toString()));
                updateListViewData();
                ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
                saveTriggers();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void saveTriggers(){
        preferences = MyPreferences.getInstance(this);
        String preferencesString = "";
        for(int i = 0; i < triggers.size(); i++){
            Trigger triggerToAdd = (Trigger) triggers.get(i);
            preferencesString  = new StringBuilder().append(preferencesString).append(triggerToAdd.name).append(",").toString();

        }
        preferences.saveStringData("triggers",preferencesString);
    }

    private void updateListViewData(){
        String[] listItems = new String[triggers.size()];
        for(int i = 0; i < triggers.size(); i++){
            Trigger trigger = (Trigger) triggers.get(i);

            listItems[i] = trigger.name;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }

    private void loadTriggers(){
        preferences = MyPreferences.getInstance(this);
        String triggerString = preferences.getStringData("triggers");
        String [] items = triggerString.split(",");
        List<String> container = Arrays.asList(items);
        for (String myString : container) {
            if (myString != "") {
                triggers.add(createTriggerFromString(myString));
            }
        }
    }

    private Trigger createTriggerFromString(String myString){
        Trigger newTrigger = new Trigger(myString);
        return  newTrigger;
    }

    public void deleteTriggerClicked(View view) {
        if (selectedTrigger != null) {
            triggers.remove(selectedTriggerIndex);
            updateListViewData();
            ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
            saveTriggers();
            selectedTrigger = null;
        }

    }
}
