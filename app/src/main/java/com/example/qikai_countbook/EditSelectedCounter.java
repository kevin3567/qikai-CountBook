package com.example.qikai_countbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Activity responsible for examining and editing the selected counter
 * @author qikai
 */
public class EditSelectedCounter extends AppCompatActivity {

    private Integer index; // Index of current counter in counters ArrayList in parent activity
    private Counter counter; // The counter examined

    // Widget instantiation
    private EditText edittext_counter_name;
    private EditText edittext_count;
    private EditText edittext_init_count;
    private EditText edittext_comment;
    private TextView textview_last_update;
    private Button button_delete_counter;
    private Button button_save_changes;
    private Button button_reset;
    private Button button_increment;
    private Button button_decrement;
    private TextView textview_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view
        setContentView(R.layout.activity_edit_selected_counter);

        Intent intent = this.getIntent();
        index = Integer.parseInt(intent.getStringExtra("Index"));
        counter = (Counter) intent.getParcelableExtra("Counter");

        // Define the widgets in accoradance to the corresponding interface
        edittext_counter_name = (EditText) findViewById(R.id.editText2);
        edittext_count = (EditText) findViewById(R.id.editText7);
        edittext_init_count = (EditText) findViewById(R.id.editText4);
        edittext_comment = (EditText) findViewById(R.id.editText5);
        textview_last_update = (TextView) findViewById(R.id.textView4);

        // initialise wdiget with appropriate value from the counter
        edittext_counter_name.setText(counter.getCounter_name());
        edittext_count.setText(Integer.toString(counter.getCount()));
        edittext_init_count.setText(Integer.toString(counter.getInit_count()));
        edittext_comment.setText(counter.getComment());
        textview_last_update.setText((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(counter.getLast_update())).toString()); // Hours, minutes, and seconds are
                                                                                                                                 // for testing purposes only
        // Define widgets that facilitate the editing of the counter
        button_delete_counter = (Button) findViewById(R.id.button2);
        button_save_changes = (Button) findViewById(R.id.button7);
        button_reset = (Button) findViewById(R.id.button5);
        button_increment = (Button) findViewById(R.id.button3);
        button_decrement = (Button) findViewById(R.id.button4);
        textview_note = (TextView) findViewById(R.id.textView10);

        // Define button for examined counter deletion
        button_delete_counter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send action of deleting the current counter to parent activity
                String action = "Delete";
                Intent intent = new Intent(EditSelectedCounter.this, DisplayCounters.class);
                intent.putExtra("Action", action);
                intent.putExtra("Index", Integer.toString(index));
                startActivity(intent);
            }
        });

        // Define button to save the changes made to the current counter
        button_save_changes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the required fields from the interface
                String counter_name_string = EditSelectedCounter.this.getEdittext_counter_name().getText().toString();
                String init_count_string = EditSelectedCounter.this.getEdittext_init_count().getText().toString();
                String comments_string = EditSelectedCounter.this.getEdittext_comment().getText().toString();
                String count_string = EditSelectedCounter.this.getEdittext_count().getText().toString();
                // Check that all necessary fields are given thte required information
                if (counter_name_string.equals("")) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Counter_Name\" field must be assigned a specific value.");
                } else if (init_count_string.equals("")) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Initial Count\" field must be assigned a specific value.");
                } else if (count_string.equals("")) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Count\" field (large box at top left) must be assigned a specific value.");
                } else {
                    // Create updated counter to be returned to parent activity
                    Counter updated_counter = new Counter(counter_name_string, Integer.parseInt(count_string), comments_string);
                    updated_counter.setInit_count(Integer.parseInt(init_count_string));
                    updated_counter.setLast_update(new Date()); // Any time the current counter states are saved, the current count is automatically considered saved,
                                                                // even if no adjustments are made in the current count field; to not change the last update field, click "<-"
                                                                // on tool bar

                    // Declare intent to update counter of specified index in parent activity
                    String action = "Update";
                    Intent intent = new Intent(EditSelectedCounter.this, DisplayCounters.class);
                    intent.putExtra("Action", action);
                    intent.putExtra("Index", Integer.toString(index));
                    intent.putExtra("Counter", updated_counter);
                    startActivity(intent);
                }

            }
        });

        // Define button to reset the current count to the initial count
        button_reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String init_count_string = ((EditText) findViewById(R.id.editText4)).getText().toString();
                if (("".equals(init_count_string))) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Initial Count\" field must be assigned a specific value.");
                } else {
                    EditSelectedCounter.this.getEdittext_count().setText(Integer.toString(Integer.parseInt(init_count_string)));
                }
            }
        });

        // Define the current count increment button
        button_increment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String string_count = EditSelectedCounter.this.getEdittext_count().getText().toString();
                if (string_count.equals("")) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Count\" field (large box at top left) must be assigned a specific value for incrementation.");
                } else {
                    int curr_count = Integer.parseInt(string_count);
                    if (curr_count + 1 >= 0) { // In case of overflow
                        edittext_count.setText(Integer.toString(curr_count + 1));
                    }
                }
            }
        });

        // Define the current count decrement button
        button_decrement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String string_count = EditSelectedCounter.this.getEdittext_count().getText().toString();
                if (string_count.equals("")) {
                    EditSelectedCounter.this.getTextview_note().setText("Error: \"Count\" field (large box at top left) must be assigned a specific value for decrementation.");
                } else {
                    int curr_count = Integer.parseInt(string_count);
                    if (curr_count - 1 >= 0) { // In case of negatives
                        edittext_count.setText(Integer.toString(curr_count - 1));
                    }
                }
            }
        });
    }

    protected void onStop() {
        super.onStop();
        this.finish();
    }

    public EditText getEdittext_counter_name() {
        return edittext_counter_name;
    }

    public EditText getEdittext_count() {
        return edittext_count;
    }

    public EditText getEdittext_init_count() {
        return edittext_init_count;
    }

    public EditText getEdittext_comment() {
        return edittext_comment;
    }

    public TextView getTextview_last_update() {
        return textview_last_update;
    }

    public Button getButton_delete_counter() {
        return button_delete_counter;
    }

    public Button getButton_save_changes() {
        return button_save_changes;
    }

    public TextView getTextview_note() {
        return textview_note;
    }

    public Button getButton_reset() {
        return button_reset;
    }

    public Button getButton_increment() {
        return button_increment;
    }

    public Button getButton_decrement() {
        return button_decrement;
    }
}
