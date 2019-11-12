package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class ViewRestaurantActivity extends AppCompatActivity {

    private final int editUpdate_CONFIG_REQUEST = 2;

    //database managers
    DatabaseHelper databaseHelper;
    Cursor databaseCursor;

    //restaurant information
    int id;
    Restaurant restaurant;

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
        id = thisIntent.getExtras().getInt("ID");

        buildInformation();
    }

    protected void buildInformation() {
        //set up database information
        databaseHelper = new DatabaseHelper(this);
        restaurant = databaseHelper.getRestaurant(id);

        //cast variables
        TextView editTitle = (TextView) findViewById(R.id.uiTxtTitleLabel);
        TextView editPrice = (TextView) findViewById(R.id.uiTxtPrice);
        TextView editRating = (TextView) findViewById(R.id.uiTxtRating);
        TextView editNotes = (TextView) findViewById(R.id.uiTxtNotes);
        TextView editTags = (TextView) findViewById(R.id.uiTxtTags);

        ProgressBar barRating = (ProgressBar) findViewById(R.id.uiBarRating);

        //casting numbers to numbers
        double decimalPrice = restaurant.getPrice();

        //format numbers
        String formatPrice  = String.format("%.02f", decimalPrice);

        //populate text information
        editTitle.setText(restaurant.getName());
        editPrice.setText("$" + formatPrice);
        editRating.setText(restaurant.getRating() + "/10");
        editNotes.setText(restaurant.getNotes());

        //restaurant is restaurant object
        //can use id
        List<Tag> tags = databaseHelper.getRestaurantsTags(id);
        String tagString = "";
        for (Tag t: tags) {
            tagString += t.getTagName();
        }

        editTags.setText(tagString);

        //get tags associated with restaurant id
        //String tags = (databaseCursor.getString(5)); //todo figure this out ryan please save us

        double decimalRating = restaurant.getRating() * 10;
        int intRating = (int) Math.round(decimalRating);

        //set rating bar amount
        barRating.setProgress(intRating);

        //sets color for rating bar
        LayerDrawable layerDrawable = (LayerDrawable) barRating.getProgressDrawable();
        Drawable progressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
        progressDrawable.setColorFilter(Color.parseColor(getProgressColor(intRating)), PorterDuff.Mode.SRC_IN);
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

    //gets color for the rating bar
    protected String getProgressColor (int rating){
        String color = "#E02121";

        //red to green
        if (rating <= 10){ color = "#E02121"; }
        if (10 < rating && rating <= 20){ color = "#E63F1A"; }
        if (20 < rating && rating <= 30){ color = "#EC5E13"; }
        if (30 < rating && rating <= 40){ color = "#F27D0D"; }
        if (40 < rating && rating <= 50){ color = "#F89C06"; }
        if (50 < rating && rating <= 60){ color = "#FFBB00"; }
        if (60 < rating && rating <= 70){ color = "#BFBE08"; }
        if (70 < rating && rating <= 80){ color = "#7FC110"; }
        if (80 < rating && rating <= 90){ color = "#3FC418"; }
        if (90 < rating ){ color = "#00C721"; }

        return color;
    }

}
