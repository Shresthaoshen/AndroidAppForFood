package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
    DatabaseHelper myDb;
    RsrtListAdapter adapter;


    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int viewBack_CONFIG_REQUEST = 2;

    private ListView rsrtListView;

    public ArrayList<Restaurant> rsrtList;

    private Context thisContext;

    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        thisContext = this;

        rsrtListView = findViewById(R.id.lstRsrt);

        rsrtList = new ArrayList<Restaurant>();

        data = myDb.getAllData();

        TextView txtEmpty = findViewById(R.id.txtEmptyList);

        if (data.getCount() == 0) {
            Toast.makeText(MainActivity.this,"empty", Toast.LENGTH_LONG).show();
        }else {
            txtEmpty.setVisibility(View.INVISIBLE);
            while(data.moveToNext()){
                String title = (data.getString(1));
                String rating = (data.getString(2));
                String price = (data.getString(3));
                String notes = (data.getString(4));
                String tags = (data.getString(5));

                Restaurant rsrt = new Restaurant(title,price,rating,notes,tags);

                rsrtList.add(rsrt);

            }
        }

        adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget2, rsrtList);
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
    }

    public void clickAdd (View view){

        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
            super.onActivityResult(requestCode, resultCode, submitData);

                //on add -> click Submit
                if (requestCode == addSubmit_CONFIG_REQUEST) {
                    if (resultCode == Activity.RESULT_OK) {

                        Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();

                        //TextView txtEmpty = findViewById(R.id.txtEmptyList);
                        //txtEmpty.setVisibility(View.INVISIBLE);

                        data = myDb.getAllData();
                        data.moveToPosition(data.getCount()-1);
                            String title = (data.getString(1));
                            String rating = (data.getString(2));
                            String price = (data.getString(3));
                            String notes = (data.getString(4));
                            String tags = (data.getString(5));

                        Restaurant rsrt = new Restaurant(title,price,rating,notes,tags);
                        rsrtList.add(rsrt);


                    }

                    if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
                    }
                }

    }

}
