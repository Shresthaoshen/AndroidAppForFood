package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    EditText editTitle, editPrice, editRating, editNotes;
    AutoCompleteTextView editTags;
    Button btnUpdate;
    TextView btnDelete;

    //position records
    int id;
    String idString;
    Restaurant restaurant;

    //tag information
    ArrayList<Tag> tagList; //list of tags pulled from the database associated with the restaurant
    ArrayList<String> currentTags; //all tags associated - this is for the autofill exclusion
    ArrayList<String> tagComplete; //all tags created - this is for the autofill
    ArrayList<String> newTags; //new tags to be added to the restaurant
    ArrayList<Integer> deletedTags; //tags to be deleted from the restaurant
    ChipGroup chipGroup;
    ArrayAdapter<String> autoCompleteAdapter;

    //update tracker
    boolean dataUpdated = false;
    boolean dataDeleted = true;
    boolean tagUpdated = false;

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

        //builds chipUI
        loadChipUI();

        //updateData when called
        updateData();

        //deleteData when called
        deleteData();
    }

    private void buildInformation(){
        restaurant = databaseHelper.getRestaurant(id);

        deletedTags = new ArrayList<>();
        currentTags = new ArrayList<>();
        newTags = new ArrayList<>();

        //cast variables
        editTitle = (EditText) findViewById(R.id.userTxtTitle);
        editPrice = (EditText) findViewById(R.id.userTxtPrice);
        editRating = (EditText) findViewById(R.id.userTxtRating);
        editNotes = (EditText) findViewById(R.id.userTxtNotes);
        editTags = (AutoCompleteTextView) findViewById(R.id.userTagText);
        chipGroup = findViewById(R.id.uiChipGroup);
        btnUpdate = (Button) findViewById(R.id.uiBtnUpdate);
        btnDelete = (TextView) findViewById(R.id.uiBtnDelete);

        //prepopulate edit fields with current info
        editTitle.setText(restaurant.getName());
        editPrice.setText(restaurant.getPrice() + "");
        editRating.setText(restaurant.getRating() + "");
        editNotes.setText(restaurant.getNotes());

        //get list of tags associated with restaurant
        tagList = databaseHelper.getRestaurantsTags(id);
        buildSavedTags(tagList);

        //turns off tag hint if there's tags
        if (tagList.size() >= 1){
            editTags.setHint("");
        }

    }

    public void updateData () {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                    for (int i = 0; i < newTags.size(); i++){
                        databaseHelper.createTag(newTags.get(i));
                        databaseHelper.createRestaurantTag(databaseHelper.getRestaurant(id).getID(), databaseHelper.getTagIDFromName(newTags.get(i)));
                    }

                    for (int i = 0; i < deletedTags.size(); i++){
                        databaseHelper.deleteTag(deletedTags.get(i));
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
        updateIntent.putExtra("tagUpdated", tagUpdated);
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

    private void buildSavedTags(final ArrayList<Tag> tagList) {
        for (int i = 0; i < tagList.size(); i++){

            final String tagName = tagList.get(i).getTagName();
            final int tagID = tagList.get(i).getId();

            //creates chip
            final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_edit, null, false);
            chip.setText(tagName);
            chip.setClickable(true);

            //adds to view
            chipGroup.addView(chip, chipGroup.getChildCount()-1);

            currentTags.add(tagList.get(i).getTagName()); //adds to autofill exclusion

            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chipGroup.removeView(chip); //removes from view
                    tagList.remove(tagName); //removes from tagList - mainly to notify that it's gone to 0
                    deletedTags.add(tagID); //adds to removal-tag groups
                    tagUpdated = true; //notifies that the tag group is updated
                }
            });
        }
    }

    private void loadChipUI(){
        //init tagList
        tagComplete = new ArrayList<String>(databaseHelper.getAllTagNames());
        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tagComplete);
        editTags.setAdapter(autoCompleteAdapter);

        //select from autocomplete
        editTags.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                addChip(adapterView.getAdapter().getItem(position).toString());
                editTags.setText(null);
            }
        });

        //done keyboard button is pressed
        editTags.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if (actionID == EditorInfo.IME_ACTION_DONE){
                    addChip(textView.getText().toString());
                    editTags.setText(null);
                    return true;
                }
                return false;
            }
        });

        //comma is detected
        editTags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0 && editable.charAt(editable.length()-1) == ','){
                    addChip(editTags.getText().toString().substring(0, editable.length()-1));
                    editTags.setText(null);
                }
            }
        });
    }

    private void addChip(String inputTag){
        if (inputTag != null && !currentTags.contains(inputTag) && !inputTag.equals("") && !inputTag.equals(" ")){
            editTags.setHint("");

            //adds to view
            addChipToGroup(inputTag);

            currentTags.add(inputTag); //adds to autofill exclusion
            newTags.add(inputTag); //adds to add-group
        }
    }

    private void addChipToGroup(final String tagName){
        //creates chip and adds it to view
        final Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.chip_edit, null, false);
            chip.setText(tagName);
            chip.setClickable(true);
        chipGroup.addView(chip, chipGroup.getChildCount()-1);

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(chip); //removes from view
                currentTags.remove(tagName); //removes from autofill exclusion
                newTags.remove(tagName); //removed from the group of tags to be added
                tagUpdated = true; //notifies that the tag group updated
            }
        });
    }

}
