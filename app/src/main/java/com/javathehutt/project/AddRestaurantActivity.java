package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddRestaurantActivity extends AppCompatActivity {
    //database managers
    DatabaseHelper databaseHelper;

    //ui managers
    EditText editTitle, editNotes, editTags, editRating, editPrice;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        //calls database constructor to create a database
        databaseHelper = new DatabaseHelper(this);

        //cast variables
        editTitle = (EditText) findViewById(R.id.userTxtTitle);
        editRating = (EditText) findViewById(R.id.userTxtRating);
        editPrice = (EditText) findViewById(R.id.userTxtPrice);
        editNotes = (EditText) findViewById(R.id.userTxtNotes);
        editTags = (EditText) findViewById(R.id.userTextTags);
        btnSubmit = (Button) findViewById(R.id.uiBtnAdd);

        SubmitData();
    }

    //onClick listener for submit button
    public void SubmitData() {

        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedCompletion()) {
                            boolean isInserted = databaseHelper.insertData(editTitle.getText().toString(),
                                    editRating.getText().toString(),
                                    editPrice.getText().toString(),
                                    editNotes.getText().toString(),
                                    editTags.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(AddRestaurantActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(AddRestaurantActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                            }

                            clickSubmit(view);
                        }
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

    private boolean checkedCompletion (){

        if (editTitle.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a title", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editRating.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a rating", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editPrice.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a price", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
