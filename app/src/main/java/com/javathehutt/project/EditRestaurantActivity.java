package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditRestaurantActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Cursor data;
    Button btnUpdate;

    //comment
    EditText rsrtTitle, rsrtRating, rsrtPrice, rsrtNotes, rsrtTags;
    int position;
    String holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Intent thisIntent = getIntent();
        position = thisIntent.getExtras().getInt("id");

        rsrtTitle = findViewById(R.id.editText_Title);
        rsrtRating = (EditText) findViewById(R.id.editText_Rating);
        rsrtPrice = (EditText) findViewById(R.id.editText_Price);
        rsrtNotes = (EditText) findViewById(R.id.editText_Notes);
        rsrtTags = (EditText) findViewById(R.id.editText_Tags);

        myDb = new DatabaseHelper(this);
        data = myDb.getAllData();

        data.moveToPosition(position);
        int ID = (data.getInt(0));

        String tags = (data.getString(5));

        rsrtTitle.setText(data.getString(1));
        rsrtRating.setText(data.getString(2));
        rsrtPrice.setText(data.getString(3));
        rsrtNotes.setText(data.getString(4));

        holder = (String.valueOf(data.getInt(0)));

        updateData();

    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    public void updateData () {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDb.updateData(holder,
                        rsrtTitle.getText().toString(),
                        rsrtPrice.getText().toString(),
                        rsrtRating.getText().toString(),
                        rsrtNotes.getText().toString(),
                        rsrtTags.getText().toString());

                clickUpdate(view);
            }
        });
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
