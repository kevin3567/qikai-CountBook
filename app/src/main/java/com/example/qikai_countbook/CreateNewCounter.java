package com.example.qikai_countbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Activity responsible for new counter creation
 * @author qikai
 */
public class CreateNewCounter extends AppCompatActivity {

    // Widget instantiation
    private EditText edittext_counter_name;
    private EditText edittext_init_count_and_count;
    private EditText edittext_comment;
    private TextView textview_note;
    private Button button_add_counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set content view
        setContentView(R.layout.activity_create_new_counter);

        // Define the widgets with repsect to the corresponding layout
        edittext_counter_name = (EditText) findViewById(R.id.editText);
        edittext_init_count_and_count = (EditText) findViewById(R.id.editText6);
        edittext_comment = (EditText) findViewById(R.id.editText11);
        textview_note = ((TextView) findViewById(R.id.textView7));
        button_add_counter = (Button) findViewById(R.id.button1);

        // Define listener for add counter button for add counter
        button_add_counter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get text from appropriate widgets
                String counter_name = (CreateNewCounter.this.getEdittext_counter_name()).getText().toString();
                String init_count_and_count = (CreateNewCounter.this.getEdittext_init_count_and_count()).getText().toString();
                String comment = (CreateNewCounter.this.getEdittext_comment()).getText().toString();
                // Check that the required fields are filled
                if (!(counter_name.equals("")) && !(init_count_and_count.equals(""))) {
                    Counter new_counter = new Counter(counter_name, Integer.parseInt(init_count_and_count), comment);
                    // Return to parent activity with intent to create new counter
                    Intent intent = new Intent(CreateNewCounter.this, DisplayCounters.class);
                    intent.putExtra("Action", "Create");
                    intent.putExtra("Counter", new_counter);
                    startActivity(intent);
                    finish();
                } else {
                    // Warn user to provide required inputs
                    CreateNewCounter.this.getTextview_note().setText("Counter cannot be added: Either (or both) \"Counter Name\" and \"Initial Count\" is not provided.");
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

    public EditText getEdittext_init_count_and_count() {
        return edittext_init_count_and_count;
    }

    public EditText getEdittext_comment() {
        return edittext_comment;
    }

    public TextView getTextview_note() {
        return textview_note;
    }

    public Button getButton_add_counter() {
        return button_add_counter;
    }
}
