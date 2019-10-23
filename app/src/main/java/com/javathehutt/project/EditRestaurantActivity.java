package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditRestaurantActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Intent thisIntent = getIntent();
        int position = thisIntent.getExtras().getInt("id");

        EditText rsrtTitle = findViewById(R.id.editText_Title);
        //EditText rsrtRating = (EditText) findViewById(R.id.txtRating);
        //EditText rsrtPrice = (EditText) findViewById(R.id.txtPrice);
        //EditText rsrtNotes = (EditText) findViewById(R.id.txtRating);

        myDb = new DatabaseHelper(this);
        data = myDb.getAllData();

        data.moveToPosition(position);
        int ID = (data.getInt(0));
        String title = (data.getString(1));
        String rating = (data.getString(2));
        String price = (data.getString(3));
        String notes = (data.getString(4));
        String tags = (data.getString(5));

        //rsrtTitle.setText(title);
        //rsrtRating.setText();
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    public void clickUpdate (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }


    public void clickDelete (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }


}
