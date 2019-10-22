package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class AddRestaurantActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTitle, editRating, editPrice, editNotes, editTags;
    Button btnAdd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        //calls database constructor to create a database
        myDb= new DatabaseHelper(this);


        //Cast variables
        editTitle = (EditText) findViewById(R.id.editText_Title);
        editRating = (EditText) findViewById(R.id.editText_Rating);
        editPrice = (EditText) findViewById(R.id.editText_Price);
        editNotes = (EditText) findViewById(R.id.editText_Notes);
        editTags = (EditText) findViewById(R.id.editText_Tags);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        AddData();

    }

    public void AddData() {
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDb.insertData(editTitle.getText().toString(),
                                editRating.getText().toString(),
                                editPrice.getText().toString(),
                                editNotes.getText().toString(),
                                editTags.getText().toString());
                        if (isInserted = true) {
                            Toast.makeText(AddRestaurantActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddRestaurantActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }

                        clickSubmit(view);
                    }
                }
        );
    }

    public void clickSubmit (View view) {
        Intent submitIntent = new Intent(this, MainActivity.class);

        setResult(RESULT_OK, submitIntent);

        finish();
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

}
