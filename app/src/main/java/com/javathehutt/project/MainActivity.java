package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



//from now own, fuck spelling - restaurant is going to be shortened to rsrt
//if the user interacts with it - user(Type)(Name)  example userTxtName
//if it's for the ui - ui(Type)(Name) example uiTxtName



public class MainActivity extends AppCompatActivity {

    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int viewBack_CONFIG_REQUEST = 2;

    private ListView rsrtListView;

    public ArrayList<Restaurant> rsrtList;

    private Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisContext = this;

        rsrtListView = findViewById(R.id.lstRsrt);

        //for testing
        Restaurant rsrt1 = new Restaurant("Rsrt 1", 4.2, 0);
        Restaurant rsrt2 = new Restaurant("Rsrt 2", 69.69, 5.12);
        Restaurant rsrt3 = new Restaurant("Rsrt 3", 500000.21, 5.3);
        Restaurant rsrt4 = new Restaurant("Rsrt 4", 0.01, 10);

        rsrtList = new ArrayList<>();
        rsrtList.add(rsrt1);
        rsrtList.add(rsrt2);
        rsrtList.add(rsrt3);
        rsrtList.add(rsrt4);

        //turns off visibility of "no entries" - manual for now we'll figure out how to automatic it later
        TextView txtEmpty = findViewById(R.id.txtEmptyList);
        if (rsrtList.size() > 0) {
            txtEmpty.setVisibility(View.INVISIBLE);
        }

        final RsrtListAdapter adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget, rsrtList);
        rsrtListView.setAdapter(adapter);


        //short click to get into view state
        rsrtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Restaurant rsrt = adapter.getItemAtPosition(position);
                Intent viewIntent = new Intent(thisContext, ViewRestaurantActivity.class);
                startActivityForResult(viewIntent, viewBack_CONFIG_REQUEST);
            }
        });


        Button addRecentButton = findViewById(R.id.addRecentButton);
        addRecentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(getApplicationContext(), AddRestaurantActivity.class);
                startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);

            }
        });

        Intent submitData = new Intent(getApplicationContext(), AddRestaurantActivity.class);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
            super.onActivityResult(requestCode, resultCode, submitData);

                //on add -> click Submit
                if (requestCode == addSubmit_CONFIG_REQUEST) {
                    if (resultCode == Activity.RESULT_OK) {

                        Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();
                    }

                    if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
                    }
                }

    }
//            protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
//
//                super.onActivityResult(requestCode, resultCode, submitData);
//
//                //on add -> click Submit
//                if (requestCode == addSubmit_CONFIG_REQUEST) {
//                    if (resultCode == RESULT_OK) {
//
//                        Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();
//                    }
//
//                    if (resultCode == RESULT_CANCELED) {
//                        Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
//                    }
//                }
//
//            }

//    public void clickAdd (View view){
//
//        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
//        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);
//
//    }


    }
