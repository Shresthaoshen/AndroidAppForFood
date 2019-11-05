package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRestaurantActivity extends AppCompatActivity {

    private final int editUpdate_CONFIG_REQUEST = 2;

    //database managers
    DatabaseHelper databaseHelper;
    Cursor databaseCursor;

    //position information
    int id;

    //watches for updated data
    boolean dataUpdated = false;
    boolean dataDeleted = false;

    //data sort types
    public String dataSortType = "ID";
    public String dataSortOrder = "DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);

        Intent thisIntent = getIntent();

        //pull ID from restaurant clicked
        id = thisIntent.getExtras().getInt("id");

        buildInformation();
    }

    protected void buildInformation() {
        //set up database information
        databaseHelper = new DatabaseHelper(this);
        databaseCursor = databaseHelper.getAllData(dataSortType, dataSortOrder);

        //cast variables
        TextView editTitle = (TextView) findViewById(R.id.uiTxtTitleLabel);
        TextView editPrice = (TextView) findViewById(R.id.uiTxtPrice);
        TextView editRating = (TextView) findViewById(R.id.uiTxtRating);
        TextView editNotes = (TextView) findViewById(R.id.uiTxtNotes);

        databaseCursor.moveToPosition(id);
        int ID = (databaseCursor.getInt(0));

        //populate text information
        editTitle.setText(databaseCursor.getString(1));
        editPrice.setText("$" + databaseCursor.getString(2));
        editRating.setText(databaseCursor.getString(3));
        editNotes.setText(databaseCursor.getString(4));
        String tags = (databaseCursor.getString(5));
    }

    public void clickItem_Edit (View view){
        Intent modifyIntent = new Intent(this, EditRestaurantActivity.class);
            modifyIntent.putExtra("id", id);
        startActivityForResult(modifyIntent, editUpdate_CONFIG_REQUEST);
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
            backIntent.putExtra("dataUpdated", dataUpdated);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent submitData) {
        super.onActivityResult(requestCode, resultCode, submitData);

        //on edit -> click Update
        if (requestCode == editUpdate_CONFIG_REQUEST) {

            if (resultCode == RESULT_OK) {
                dataUpdated = submitData.getExtras().getBoolean("dataUpdated");
                dataDeleted = submitData.getExtras().getBoolean("dataDeleted");

                if (dataUpdated){
                    buildInformation();
                    Toast.makeText(this, "switched over from edit", Toast.LENGTH_LONG).show();
                }

                if (dataDeleted){
                    Toast.makeText(this, "switched from delete", Toast.LENGTH_LONG).show();
                    Intent deleteIntent = new Intent(this, MainActivity.class);
                        deleteIntent.putExtra("dataDeleted", dataDeleted);
                        setResult(RESULT_CANCELED, deleteIntent);
                    finish();
                }

            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
            }

        }
    }

}
