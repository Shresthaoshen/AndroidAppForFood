package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class AddRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.javathehutt.project.R.layout.activity_add_restaurant);
    }

    private void createRestaurant(){

    }

    public void clickSubmit (View view) {

        Intent submitIntent = new Intent(this, MainActivity.class);
            setResult(RESULT_OK, submitIntent);
            finish();
    }
}
