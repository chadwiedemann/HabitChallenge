package com.chadwiedemann.habittracker;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.R.id.list;
import static java.security.AccessController.getContext;

public class CravingMeterActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    ArrayList cravings = new ArrayList();
    private ListView mListView;
    CravingAdapter adapter;
    Craving selectedCraving;
    int selectedCravingIndex;
    private MyPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craving_meter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.cravings_list_view);
        final SeekBar sk=(SeekBar) findViewById(R.id.craving_level_seekbar);
        sk.setOnSeekBarChangeListener(this);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectedCraving = (Craving) cravings.get(position);
                selectedCravingIndex = position;
                sk.setProgress(selectedCraving.intensity);

            }

        });
        loadCravings();
        adapter = new CravingAdapter(this, R.layout.triger_row, cravings);
        mListView.setAdapter(adapter);

    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void addCravingClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input Craving Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               cravings.add(new Craving(input.getText().toString(), 0) );
                adapter.notifyDataSetChanged();
                saveCravings();

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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (selectedCraving != null) {
            selectedCraving.intensity = progress;
            adapter.notifyDataSetChanged();
            saveCravings();
        }

    }

    private void saveCravings(){
        preferences = MyPreferences.getInstance(this);
        String preferencesString = "";
        for(int i = 0; i < cravings.size(); i++){
            Craving cravingToAdd = (Craving) cravings.get(i);
            preferencesString  = new StringBuilder().append(preferencesString).append(cravingToAdd.name).append(cravingToAdd.intensity).append(",").toString();

        }
        preferences.saveStringData("cravings",preferencesString);
    }

    private void loadCravings(){
        preferences = MyPreferences.getInstance(this);
        String cravingString = preferences.getStringData("cravings");
        String [] items = cravingString.split(",");
        List<String> container = Arrays.asList(items);
        for (String myString : container) {
            if (myString != "") {
                cravings.add(createCravingFromString(myString));
            }
        }
    }

    private Craving createCravingFromString(String myString){
        Craving newCraving = new Craving(myString.substring(0, myString.length() - 1), Integer.parseInt(myString.substring(myString.length() - 1)));
        return  newCraving;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void deleteCravingClicked(View view) {
        if (selectedCraving != null) {
            cravings.remove(selectedCravingIndex);
            adapter.notifyDataSetChanged();
            saveCravings();
            selectedCraving = null;
        }
    }

    public class CravingAdapter extends ArrayAdapter{

        private List<Craving> cravingList;
        private int resource;
        private LayoutInflater inflater;
        int selectedPosition = 0;


        public CravingAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Craving> objects) {
            super(context, resource, objects);
            cravingList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView cravingText;
            TextView cravingLevelText;
            RadioGroup buttonGroup;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.triger_row, null);
            }
            cravingText = (TextView) convertView.findViewById(R.id.craving_name_text_view);
            Craving newCraving = cravingList.get(position);
            cravingText.setText(newCraving.name);
            cravingLevelText = (TextView) convertView.findViewById(R.id.craving_value_text_view);
            cravingLevelText.setText( String.valueOf(newCraving.intensity));
            return convertView;
        }
    }
}
