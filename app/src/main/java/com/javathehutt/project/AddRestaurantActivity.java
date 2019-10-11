package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AddRestaurantActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void clickSubmit (View view) {
        Intent submitIntent = new Intent(this, MainActivity.class);

        submitIntent.putExtra("rsrtName", "added a new one, boys");
        submitIntent.putExtra("rsrtPrice", 5.0);
        submitIntent.putExtra("rsrtRating", 3.4);

        setResult(RESULT_OK, submitIntent);

        finish();
    }

}
