package com.example.qikai_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Activity responsible for displaying counters and operates as parent activity for all other activities
 * @author qikai
 */
public class DisplayCounters extends AppCompatActivity {

    private static final String SAVEFILECOUNTER = "counterfile.sav"; // save file
    private ArrayList<Counter> counters; // array of counters
    private ArrayAdapter<Counter> adapter; // adapter for the array of counters

    // widget instantiation
    private ListView listview_counters;
    private Button button_new_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // System.out.println("Creating");
        super.onCreate(savedInstanceState);
        // Restore previous state
        loadFromFile();
        // Set content view
        setContentView(R.layout.activity_display_counters);

        // Define the required fields
        adapter = new ArrayAdapter<Counter>(this, android.R.layout.simple_list_item_1, counters);
        // Define the widgets with repsect to the corresponding layout
        listview_counters = (ListView) findViewById(R.id.listView);
        Button button_new_counter = (Button) findViewById(R.id.button);

        // Define listener for selecting counter on listview of counters
        listview_counters.setAdapter(adapter);
        listview_counters.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long l){
                Counter counter = (Counter) DisplayCounters.this.getListview_counters().getItemAtPosition(position);
                Intent intent = new Intent(DisplayCounters.this, EditSelectedCounter.class);
                intent.putExtra("Index", Integer.toString(position));
                intent.putExtra("Counter", counter);
                startActivity(intent);
            }
        });

        // Define listener for button commencing procedure for creating new counter
        button_new_counter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DisplayCounters.this, CreateNewCounter.class);
                startActivity(intent);
            }
        });

        // If the action is not null (transition from another activity), such action will be performed
        Intent intent = this.getIntent();
        String action = intent.getStringExtra("Action");
        if (action != null) {
            if (action.equals("Create")) { // Create counter from intent provided resources
                Counter new_counter = (Counter) intent.getParcelableExtra("Counter");
                counters.add(new_counter);
                intent.removeExtra("Counter");
            }
            else if (action.equals("Delete")) { // Delete counter from intent provided resources
                int index = Integer.parseInt(intent.getStringExtra("Index"));
                counters.remove(index);
                intent.removeExtra("Index");
            }
            else if (action.equals("Update")) { // Delete counter from intent provided resources
                int index = Integer.parseInt(intent.getStringExtra("Index"));
                Counter updated_counter = (Counter) intent.getParcelableExtra("Counter");
                counters.set(index, updated_counter);
                intent.removeExtra("Index");
                intent.removeExtra("Counter");
            }
            intent.removeExtra("Action");
        }
        TextView counter_summary = (TextView) findViewById(R.id.textView3);
        counter_summary.setText("Current Counter Amount: " + Integer.toString(counters.size())); // counter amount summary
        saveToFile(); // In case of unforeseen shutdown, after the listview of counters is already updated
    }

    @Override
    protected void onStop() {
        // System.out.println("Stopping");
        super.onStop();
        saveToFile();
    }

    // Move to background saving procedure
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // System.out.println("InstanceSaving");
        // Save instance
        savedInstanceState.putParcelableArrayList("past_counters", this.counters);
        super.onSaveInstanceState(savedInstanceState);
    }

    // Move to foreground restoring procedure
    @ Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // System.out.println("InstanceRestoring");
        // Restore instance
        super.onRestoreInstanceState(savedInstanceState);
        this.counters = savedInstanceState.getParcelableArrayList("past_counters");
    }

    // Opening loading procedure
    // Save curent state on file
    // Taken from ECE 301 Lab2 TA demonstration code
    private void loadFromFile() {
        // System.out.println("FileRestoring");
        try {
            FileInputStream fis = openFileInput(SAVEFILECOUNTER);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>(){}.getType();
            this.counters = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            this.counters = new ArrayList<Counter>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Closing saving procedure
    // Save curent state on file
    // Taken from ECE301 Lab2 TA demonstration code
    private void saveToFile() {
        // System.out.println("FileSaving");
        try {
            FileOutputStream fos = openFileOutput(SAVEFILECOUNTER, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.counters, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListView getListview_counters() {
        return listview_counters;
    }

    public Button getButton_new_counter() {
        return button_new_counter;
    }
}
