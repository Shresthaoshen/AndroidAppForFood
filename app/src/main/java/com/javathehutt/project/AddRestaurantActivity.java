package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddRestaurantActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_restaurant);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

    }

    public void clickSubmit (View view) {

        databaseManager.insert(createRsrt());

        Toast.makeText(this, "switched over", Toast.LENGTH_LONG).show();

        Intent submitIntent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, submitIntent);
            finish();
    }

    public Restaurant createRsrt() {

        EditText txtTitle = (EditText)findViewById(R.id.txtTitle);
        //EditText numRating = findViewById(R.id.numRating);
        //EditText numPrice = findViewById(R.id.numPrice);
        //TextInputEditText txtNotes = findViewById(R.id.txtNotes);

        //String titleTxt = txtTitle.getText().toString().trim();
        Toast.makeText(this, "a toast", Toast.LENGTH_LONG).show();

        //Restaurant newRsrt = new Restaurant(txtTitle.getText().toString());
        //newRsrt.setRating(numRating.getInputType());
       // newRsrt.setPrice(numPrice.getInputType());
       // newRsrt.setNotes(txtNotes.getTextColors().toString());*/

        Restaurant newRsrt = new Restaurant("title");
        newRsrt.setRating(0.1);
        newRsrt.setPrice(3.4);
        newRsrt.setNotes("noteees");

        return newRsrt;
    }
}
