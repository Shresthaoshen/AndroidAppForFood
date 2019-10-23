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
    DatabaseHelper myDb;
    Cursor data;

    int id;

    //watches for updated data
    boolean dataUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);

        Intent thisIntent = getIntent();
        id = thisIntent.getExtras().getInt("id");

        buildInformation();
    }

    protected void buildInformation() {
        TextView rsrtTitle = (TextView) findViewById(R.id.uiTxtTitleLabel);
        TextView rsrtRating = (TextView) findViewById(R.id.uiTxtRating);
        TextView rsrtPrice = (TextView) findViewById(R.id.uiTxtPrice);
        TextView rsrtNotes = (TextView) findViewById(R.id.uiTxtNotes);

        myDb = new DatabaseHelper(this);
        data = myDb.getAllData();

        data.moveToPosition(id);
        int ID = (data.getInt(0));
        String title = (data.getString(1));
        String rating = (data.getString(2));
        String price = (data.getString(3));
        String notes = (data.getString(4));
        String tags = (data.getString(5));


        rsrtTitle.setText(title);
        rsrtRating.setText(rating);
        rsrtPrice.setText(price);
        rsrtNotes.setText(notes);
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

                if (dataUpdated){
                    buildInformation();
                }

                Toast.makeText(this, "switched over from edit", Toast.LENGTH_LONG).show();
            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
            }

        }
    }

}
