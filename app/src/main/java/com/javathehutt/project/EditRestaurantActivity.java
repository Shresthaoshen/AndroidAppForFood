package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    public void clickUpdate (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }


    public void clickDelete (View view){
        Intent backIntent = new Intent(this, ViewRestaurantActivity.class);
        setResult(RESULT_OK, backIntent);
        finish();
    }


}
