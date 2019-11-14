package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class EditRestaurantActivity extends AppCompatActivity {

    //database managers
    DatabaseHelper databaseHelper;

    //UI Managers
    EditText editTitle, editPrice, editRating, editNotes, editTags;
    Button btnUpdate;
    TextView btnDelete;

    //position records
    int id;
    String idString;
    Restaurant restaurant;

    //tag information
    ArrayList<Tag> tagList;
    ChipGroup chipGroup;

    //update tracker
    boolean dataUpdated = false;
    boolean dataDeleted = true;
    boolean tagUpdated = false;

    //stuff for set tags
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        //set up database managers
        databaseHelper = new DatabaseHelper(this);

        //get ID information from viewIntent
        Intent thisIntent = getIntent();
        id = thisIntent.getExtras().getInt("id");
        idString = id + "";

        //builds information
        buildInformation();

        //updateData when called
        updateData();

        //deleteData when called
        deleteData();
    }

    private void buildInformation(){
        restaurant = databaseHelper.getRestaurant(id);


        //cast variables
        editTitle = (EditText) findViewById(R.id.userTxtTitle);
        editPrice = (EditText) findViewById(R.id.userTxtPrice);
        editRating = (EditText) findViewById(R.id.userTxtRating);
        editNotes = (EditText) findViewById(R.id.userTxtNotes);
        editTags = (EditText) findViewById(R.id.userTextTags);
        chipGroup = findViewById(R.id.uiChipGroup);
        btnUpdate = (Button) findViewById(R.id.uiBtnUpdate);
        btnDelete = (TextView) findViewById(R.id.uiBtnDelete);

        //prepopulate edit fields with current info
        editTitle.setText(restaurant.getName());
        editPrice.setText(restaurant.getPrice() + "");
        editRating.setText(restaurant.getRating() + "");
        editNotes.setText(restaurant.getNotes());
        //String tags = (databaseCursor.getString(5)); //todo get tags

        //get list of tags associated with restaurant
        tagList = databaseHelper.getRestaurantsTags(id);
        setTags(tagList);

    }

    public void updateData () {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String priceText = editPrice.getText().toString();

                if (checkedCompletion()) {
                    boolean isUpdate = databaseHelper.updateData(idString,
                            editTitle.getText().toString(),
                            (Double) parseDouble(editPrice.getText().toString()),
                            (Double) parseDouble(editRating.getText().toString()),
                            editNotes.getText().toString());

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
                boolean isDelete = databaseHelper.deleteData(idString);

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
            backIntent.putExtra("tagUpdated", tagUpdated);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

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


    private void setTags(final ArrayList<Tag> tagList) {
        for (i = 0; i < tagList.size(); i++){

            final String tagName = tagList.get(i).getTagName();
            final int tagID = tagList.get(i).getId();

            final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_edit, null, false);
            chip.setText(tagName);
            chipGroup.addView(chip, chipGroup.getChildCount());
            chip.setClickable(true);

            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chipGroup.removeView(chip);
                    databaseHelper.deleteTag(tagID);
                    tagList.remove(tagName);
                    tagUpdated = true;
                }
            });
        }
    }





}
