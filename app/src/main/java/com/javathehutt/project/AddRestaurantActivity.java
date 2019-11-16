package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;


public class AddRestaurantActivity extends AppCompatActivity {
    //database managers
    DatabaseHelper databaseHelper;

    //ui managers
    EditText editTitle, editPrice, editRating, editNotes;
    AutoCompleteTextView editTags;
    Button btnSubmit;
    ChipGroup chipGroup;

    //tag managers
    String[] tagArray;
    ArrayList<String> currentTags;
    ArrayList<String> tagComplete;
    ArrayAdapter<String> autoCompleteAdapter;

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
        editTags = (AutoCompleteTextView) findViewById(R.id.userTagText);
        chipGroup = (ChipGroup) findViewById(R.id.uiChipGroup);
        btnSubmit = (Button) findViewById(R.id.uiBtnAdd);

        loadChipUI();

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

    private void loadChipUI(){
        //init tagList
        tagComplete = new ArrayList<String>(databaseHelper.getAllTagNames());
        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tagComplete);
        editTags.setAdapter(autoCompleteAdapter);
        editTags.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                addChip(adapterView.getAdapter().getItem(position).toString());
                editTags.setText(null);
            }
        });


    }

    private void addChip(String inputTag){
        if (inputTag != null && !currentTags.contains(inputTag)){
            addChipToGroup(inputTag);
            currentTags.add(inputTag);
        }
    }

    private void addChipToGroup(String tagName){
        final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_edit, null, false);
            chip.setText(tagName);
            chip.setClickable(true);
        chipGroup.addView(chip, chipGroup.getChildCount());

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(chip);

            }
        });
    }



}
