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

    EditText rsrtTitle, rsrtNotes, rsrtTags, rsrtRating, rsrtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Intent thisIntent = getIntent();
        int position = thisIntent.getExtras().getInt("id");

        rsrtTitle = findViewById(R.id.editText_Title);
        rsrtRating = (EditText) findViewById(R.id.editText_Rating);
        rsrtPrice = (EditText) findViewById(R.id.editText_Price);
        rsrtNotes = (EditText) findViewById(R.id.editText_Notes);

        myDb = new DatabaseHelper(this);
        data = myDb.getAllData();

        data.moveToPosition(position);
        int ID = (data.getInt(0));

        String tags = (data.getString(5));

        rsrtTitle.setText(data.getString(1));
        rsrtRating.setText(data.getString(2));
        rsrtPrice.setText(data.getString(3));
        rsrtNotes.setText(data.getString(4));
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    public void clickUpdate (View view){
        updateDataEntry();
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }


    public void clickDelete (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }

    protected void updateDataEntry(){
        /*
        myDb.insertData(rsrtTitle.getText().toString(),
                                rsrtRating.getText().toString(),
                                rsrtPrice.getText().toString(),
                                rsrtNotes.getText().toString(),
                                editTags.getText().toString());
       */
    }


}
