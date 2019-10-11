package com.javathehutt.project;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//from now own, fuck spelling - restaurant is going to be shortened to rsrt
//if the user interacts with it - user(Type)(Name)  example userTxtName
//if it's for the ui - ui(Type)(Name) example uiTxtName



public class MainActivity extends AppCompatActivity {

    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int editUpdate_CONFIG_REQUEST = 2;
    private final int back_CONFIG_REQUEST = 3;

    private ListView rsrtListView;

    public ArrayList<Restaurant> rsrtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rsrtListView = findViewById(R.id.lstRsrt);

            //for testing
            Restaurant rsrt1 = new Restaurant("1", 4.5, 5);
            Restaurant rsrt2 = new Restaurant("2", 4.5, 5);
            Restaurant rsrt3 = new Restaurant("3", 4.5, 5);
            Restaurant rsrt4 = new Restaurant("4", 4.5, 5);

            rsrtList = new ArrayList<>();
            rsrtList.add(rsrt1);
            rsrtList.add(rsrt2);
            rsrtList.add(rsrt3);
            rsrtList.add(rsrt4);

            //turns off visibility of "no entries" - manual for now we'll figure out how to automatic it later
            TextView txtEmpty = (TextView)findViewById(R.id.txtEmptyList);
            if (rsrtList.size() > 0){
                txtEmpty.setVisibility(View.INVISIBLE);
            }

        RsrtListAdapter adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget, rsrtList);
        rsrtListView.setAdapter(adapter);

    }

    public void clickAdd (View view){
        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
        //on add -> click Submit
        if (requestCode == addSubmit_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {

                String name = submitData.getExtras().getString("rsrtName");
                Double price = submitData.getExtras().getDouble("rsrtPrice");
                Double rating = submitData.getExtras().getDouble("rsrtRating");

                //Restaurant newRsrt = new Restaurant(name, price,rating);

                //rsrtList.add(newRsrt);

                Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();
            }
        }

        //on edit -> click Update
        if (requestCode == editUpdate_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "switched over from edit", Toast.LENGTH_LONG).show();
            }
        }

        //on edit or add -> click back
        if (requestCode == back_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void clickItem_Edit (View view){
        Intent modifyIntent = new Intent(this, EditRestaurantActivity.class);
        startActivityForResult(modifyIntent, editUpdate_CONFIG_REQUEST);
    }






}
