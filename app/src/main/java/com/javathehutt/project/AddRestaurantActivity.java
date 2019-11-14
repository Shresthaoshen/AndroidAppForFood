package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;


public class AddRestaurantActivity extends AppCompatActivity {
    //database managers
    DatabaseHelper databaseHelper;

    //ui managers
    EditText editTitle, editPrice, editRating, editNotes, editTags;
    Button btnSubmit;

    //tag array
    String[] tagArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        //calls database constructor to create a database
        databaseHelper = new DatabaseHelper(this);

        //cast variables
        editTitle = (EditText) findViewById(R.id.userTxtTitle);
        editPrice = (EditText) findViewById(R.id.userTxtPrice);
        editRating = (EditText) findViewById(R.id.userTxtRating);
        editNotes = (EditText) findViewById(R.id.userTxtNotes);
        editTags = (EditText) findViewById(R.id.userTxtTags);
        btnSubmit = (Button) findViewById(R.id.uiBtnAdd);

        //init tagList
//        tagList = new ArrayList<>();

        SubmitData();
    }

    //onClick listener for submit button
    public void SubmitData() {

        btnSubmit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkedCompletion()) {
                        String tags = editTags.getText().toString();
                        tagArray = tags.split("\\s*,\\s*");

                        boolean isInserted = databaseHelper.createRestaurant(editTitle.getText().toString(),
                                (Double) parseDouble(editPrice.getText().toString()),
                                (Double) parseDouble(editRating.getText().toString()),
                                editNotes.getText().toString(),
                                tagArray);
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

    //onClick submit - ends activity, submits data
    public void clickSubmit (View view) {
        Intent submitIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_OK, submitIntent);
        finish();
    }

    //onClick back - ends activity, does not submit data
    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    //checks to make sure all fields are filled out - avoid null errors
    private boolean checkedCompletion (){

        if (editTitle.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a title", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editPrice.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a price", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (editRating.getText().toString().trim().length() == 0){
            Toast.makeText(this, "You did not enter a rating", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void makeChips(){

    }

}
