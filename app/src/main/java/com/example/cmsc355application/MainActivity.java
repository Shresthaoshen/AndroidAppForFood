package com.example.cmsc355application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //screen is populated with resturants when you open it up

    //clicking new brings you to a new resturant page

    //clicking on a resturant brings you to the view resturants

    //screen is repopulated, including the new resturant, when you go back to the main page 

}
