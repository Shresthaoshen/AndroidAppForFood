package com.example.cmsc355application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int addSubmit_CONFIG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickAdd (View view){
        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);
    }

    protected void onAddResult(int requestCode, int resultCode, Intent submitData){
        if (requestCode == addSubmit_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {

                //String createdResturant = submitData.getStringExtra("");

                Toast.makeText(this, "switched over", Toast.LENGTH_LONG).show();

            }

        }
    }



    //screen is populated with resturants when you open it up

    //clicking new brings you to a new resturant page

    //clicking on a resturant brings you to the view resturants

    //screen is repopulated, including the new resturant, when you go back to the main page 

}
