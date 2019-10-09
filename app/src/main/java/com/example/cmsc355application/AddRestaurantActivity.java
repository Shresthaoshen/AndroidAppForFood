package com.example.cmsc355application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
    }

    private Restaurant createRestaurant(){

        TextInputEditText txtTitle = findViewById(R.id.txtTitle);
        EditText numRating = findViewById(R.id.numRating);
        EditText numPrice = findViewById(R.id.numPrice);
        TextInputEditText txtNotes = findViewById(R.id.txtNotes);


        Restaurant newRestaurant = new Restaurant(txtTitle.getText().toString());
            newRestaurant.setRating(numRating.getInputType());
            newRestaurant.setPrice(numPrice.getInputType());
            newRestaurant.setNotes(txtNotes.getTextColors().toString());

        return newRestaurant;
    }

    public void clickSubmit (View view) {
        //Restaurant newRestaurant = createRestaurant(); //THIS IS BROKEN - if you comment it out it works idk what's going on

        Intent submitIntent = new Intent(this, MainActivity.class);
            //submitIntent.putExtra("ResturantCreated", newRestaurant.toString());
            setResult(RESULT_OK, submitIntent);

            finish();
    }
}
