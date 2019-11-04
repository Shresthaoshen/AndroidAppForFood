package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditRestaurantActivity extends AppCompatActivity {

    //database managers
    DatabaseHelper databaseHelper;
    Cursor databaseCursor;

    //UI Managers
    EditText editTitle, editRating, editPrice, editNotes, editTags;
    Button btnUpdate;
    TextView btnDelete;

    //position records
    int position;
    String positionString;

    //update tracker
    boolean dataUpdated = false;
    boolean dataDeleted = true;

    //data sort types
    public String dataSortType = "ID";
    public String dataSortOrder = "DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        //set up database managers
        databaseHelper = new DatabaseHelper(this);
        databaseCursor = databaseHelper.getAllData(dataSortType, dataSortOrder);

        //get ID information from viewIntent
        Intent thisIntent = getIntent();
        position = thisIntent.getExtras().getInt("id");

        //cast variables
        editTitle = (EditText) findViewById(R.id.userTxtTitle);
        editRating = (EditText) findViewById(R.id.userTxtRating);
        editPrice = (EditText) findViewById(R.id.userTxtPrice);
        editNotes = (EditText) findViewById(R.id.userTxtNotes);
        editTags = (EditText) findViewById(R.id.userTextTags);

        btnUpdate = (Button) findViewById(R.id.uiBtnUpdate);
        btnDelete = (TextView) findViewById(R.id.uiBtnDelete);

        //move cursor to position
        databaseCursor.moveToPosition(position);

        //get ID of current and cast to String
        int ID = (databaseCursor.getInt(0));
        positionString = (String.valueOf(databaseCursor.getInt(0)));

        //prepopulate edit fields with current info
        editTitle.setText(databaseCursor.getString(1));
        editRating.setText(databaseCursor.getString(2));
        editPrice.setText(databaseCursor.getString(3));
        editNotes.setText(databaseCursor.getString(4));
        String tags = (databaseCursor.getString(5));

        //updateData when called
        updateData();

        //deleteData when called
        deleteData();

    }

    public void updateData () {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkedCompletion()) {
                    boolean isUpdate = databaseHelper.updateData(positionString,
                            editTitle.getText().toString(),
                            editRating.getText().toString(),
                            editPrice.getText().toString(),
                            editNotes.getText().toString(),
                            editTags.getText().toString());

                    if (isUpdate == true) {
                        Toast.makeText(EditRestaurantActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        dataUpdated = true;
                    } else {
                        Toast.makeText(EditRestaurantActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                    }

                    clickUpdate(view);
                }
            }
        });
    }

    public void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditRestaurantActivity.this, "Deleted Clicked", Toast.LENGTH_SHORT).show();
                boolean isDelete = databaseHelper.deleteData(positionString);

                if (isDelete == true) {
                    Toast.makeText(EditRestaurantActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    dataDeleted = true;
                } else {
                    Toast.makeText(EditRestaurantActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                }

                clickDelete(view);
            }
        });
    }


    public void clickUpdate (View view){
        Intent updateIntent = new Intent(this, ViewRestaurantActivity.class);
            updateIntent.putExtra("dataUpdated", dataUpdated);
        setResult(RESULT_OK, updateIntent);
        finish();
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
            dataUpdated = false;
            backIntent.putExtra("dataUpdated", dataUpdated);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    //TODO
    public void clickDelete (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
            dataDeleted = true;
            backIntent.putExtra("dataDeleted", dataDeleted);
        setResult(RESULT_OK, backIntent);
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
