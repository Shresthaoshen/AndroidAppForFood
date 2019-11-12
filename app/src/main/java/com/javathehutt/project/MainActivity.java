package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.Integer.parseInt;


//restaurant is going to be shortened to rsrt
//if the user interacts with it for data reasons - user(Type)(Name)  example userTxtName
//if it's for the ui - ui(Type)(Name) example uiTxtName (this includes activity changers like buttons - it only effects the UI, data is not stored)

public class MainActivity extends AppCompatActivity {

    //to-do - get price formatting correctly when adding
    //to-do - rating bar ui in most (if not all) restaurant views
    //to-do - tree system for tags - figure out how to do them as autofill/bubbles

    //database managers
    private DatabaseHelper databaseHelper;
    private Cursor databaseCursor;

    //activity managers
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int viewBack_CONFIG_REQUEST = 2;
    private final int settingsBack_CONFIG_REQUEST = 3;

    //list managers
    private ListView uiListView;
    private RsrtListAdapter adapter;

    //price trackers
        //array lines up with min/max prices - [0] is $, [5] is $$$$$
    public double[] priceScale = new double[]{10000, 0, 0, 0, 0};
    public ArrayList<Double> priceList= databaseHelper.getPriceList;
    public double priceAverage;

    //self-reference context
    private Context thisContext;

    //data trackers
    private boolean dataUpdated = false;
    private boolean dataDeleted = false;
    public String dataSortType = "ID";
    public String dataSortOrder = "DESC";

    //settings trackers
    //update w databasehelper
    private boolean settingPriceNumber = true;

    //ui elements
    private Spinner uiSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //self-references context for onItemClick
        thisContext = this;

        //populated spinner list
        uiSpinner = findViewById(R.id.uiDropSort);
        populateSpinner();

        //update list of restaurants displayed
        updateList();

        //onClick listeners for list objects to get into viewRestaurant activity
        uiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent viewIntent = new Intent(thisContext, ViewRestaurantActivity.class);

                //int ID = adapter.getItem(position).getID();

                viewIntent.putExtra("ID", position);
                //viewIntent.putExtra("position", position);

                startActivityForResult(viewIntent, viewBack_CONFIG_REQUEST);
            }
        });

    }

    //onClick for add button - just starts addRestaurant activity
    public void clickAdd (View view){
        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);

    }

    //onClick for settings button - opens setting drawer
    public void clickSettings (View view){
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        settingsIntent.putExtra("priceScale", priceScale);
        startActivityForResult(settingsIntent, settingsBack_CONFIG_REQUEST);
    }

    //result
    protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
            super.onActivityResult(requestCode, resultCode, submitData);

                //on add activity -> click Submit
                if (requestCode == addSubmit_CONFIG_REQUEST) {
                    if (resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();

                        //updates ListView
                        updateList();

                    }

                    //on add activity -> clicked Back
                    if (resultCode == RESULT_CANCELED) {
                        Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
                    }
                }

                //on view activity -> click Back
                if (requestCode == viewBack_CONFIG_REQUEST){
                    if (resultCode == Activity.RESULT_CANCELED) {

                        //checks to see if any data was modified while in the activity
                        dataUpdated = submitData.getExtras().getBoolean("dataUpdated");
                        dataDeleted = submitData.getExtras().getBoolean("dataDeleted");

                        if (dataUpdated || dataDeleted){
                            updateList();
                        }

                        Toast.makeText(this, "switched over from view", Toast.LENGTH_LONG).show();

                    }
                }

                //on settings activity -> click Back
                if (requestCode == settingsBack_CONFIG_REQUEST){
            if (resultCode == Activity.RESULT_CANCELED) {

                //checks to see if any data was modified while in the activity
                settingPriceNumber = submitData.getExtras().getBoolean("settingsPriceNumber");

                updateList();

                Toast.makeText(this, "switched over from settings and setting is now" + settingPriceNumber, Toast.LENGTH_LONG).show();

            }
        }

    }


    //new updates Field below Spinner, depending on pa
    protected void updateList(){
        databaseHelper = new DatabaseHelper(thisContext);
        List<Restaurant> restaurantList = databaseHelper.getAllRestaurants(dataSortType, dataSortOrder);

        uiListView = findViewById(R.id.uiListRsrt);
        TextView uiTxtEmpty = findViewById(R.id.uiTxtEmpty);
        priceList = new ArrayList<Double>();


        //checks if list is empty
        if (restaurantList.isEmpty()) {
            Toast.makeText(MainActivity.this,"empty", Toast.LENGTH_LONG).show();
            uiTxtEmpty.setVisibility(View.VISIBLE);
        } else {
            //populates listView from rsrtArrayList
            adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget, restaurantList, priceScale, settingPriceNumber);
            uiListView.setAdapter(adapter);

        }
    }

//    //list manager - handles populating the list and updating it
//    protected void updateRecentList(){
//        //initializes database managers
//        databaseHelper = new DatabaseHelper(thisContext);
//        databaseCursor = databaseHelper.getAllData(dataSortType, dataSortOrder); //i was going to pass this stuff into here for the sort but it's fighting me
//
//        //initialized ui elements
//        uiListView = findViewById(R.id.uiListRsrt);
//        TextView uiTxtEmpty = findViewById(R.id.uiTxtEmpty);
//
//        //creates a new ArrayList of type Restaurant
//        rsrtArrayList = new ArrayList<>();
//
//        //creates empty price list
//        priceList = new ArrayList<Double>();
//
//        //notifies of an empty restaurant list
//        if (databaseCursor.getCount() == 0) {
//            Toast.makeText(MainActivity.this,"empty", Toast.LENGTH_LONG).show();
//            uiTxtEmpty.setVisibility(View.VISIBLE);
//        }
//
//        else {
//            //hides empty restaurant message
//            uiTxtEmpty.setVisibility(View.INVISIBLE);
//
//            //populates restaurant object to be displayed on list view
//            while(databaseCursor.moveToNext()){
//                int ID = (databaseCursor.getInt(0));
//                String title = (databaseCursor.getString(1));
//                Double price = (databaseCursor.getDouble(2));
//                Double rating = (databaseCursor.getDouble(3));
//                String notes = (databaseCursor.getString(4));
//                String tags = (databaseCursor.getString(5));
//
//
//
//                //finds the average
//                //priceList.add(Double.parseDouble(price));
//                priceList.add(price);
//
//                rsrtArrayList.add(new Restaurant(ID, title,price,rating,notes,tags));
//            }
//
//        }
//
//        if (priceList.size() > 0){
//            Collections.sort(priceList);
//            priceAverage = 0;
//
//            for (int i = 0; i < priceList.size(); i++){
//                priceAverage += priceList.get(i);
//            }
//
//            priceAverage = priceAverage/(priceList.size());
//
//            //min ($)
//            priceScale[0] = priceList.get(0);
//            //max ($$$$$)
//            priceScale[4] = priceList.get(priceList.size()-1);
//            //average ($$$)
//            priceScale[2] = priceAverage;
//            //min average ($$)
//            priceScale[1] = (priceAverage+priceScale[0])/2;
//            //max average ($$$$)
//            priceScale[3] = (priceAverage+priceScale[4])/2;
//        }
//
//        //populates listView from rsrtArrayList
//        adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget, rsrtArrayList, priceScale, settingPriceNumber);
//        uiListView.setAdapter(adapter);
//
//    }

    //spinner manager - handled populating the spinner of sort by choices
    protected void populateSpinner(){
        String[] sortTypes = new String[]{"Recents", "Price - Low to High", "Price - High to Low", "Rating - Low to High", "Rating - High to Low", "Oldest"};

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, R.layout.spinner_widget, sortTypes);
        spinAdapter.setDropDownViewResource(R.layout.spinner_dropdown_widget);
        uiSpinner.setAdapter(spinAdapter);

        //sets up a listener and changes the sort keywords based on the case - case aligns with index of spinAdapter array
        uiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        dataSortType = "ID"; //sort type
                        dataSortOrder = "DESC"; //sort order
                        Toast.makeText(thisContext, "selected 0", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                    case 1:
                        dataSortType = "PRICE";
                        dataSortOrder = "ASC";
                        Toast.makeText(thisContext, "selected 1", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                    case 2:
                        dataSortType = "PRICE";
                        dataSortOrder = "DESC";
                        Toast.makeText(thisContext, "selected 2", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                    case 3:
                        dataSortOrder = "RATING";
                        dataSortOrder = "ASC";
                        Toast.makeText(thisContext, "selected 3", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                    case 4:
                        dataSortType = "RATING";
                        dataSortOrder = "DESC";
                        Toast.makeText(thisContext, "selected 4", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                    case 5:
                        dataSortType = "ID";
                        dataSortOrder = "ASC";
                        Toast.makeText(thisContext, "selected 4", Toast.LENGTH_SHORT).show();
                        updateList(); //updates list
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
