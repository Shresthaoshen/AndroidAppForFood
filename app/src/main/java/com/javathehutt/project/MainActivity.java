package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//restaurant is going to be shortened to rsrt
//if the user interacts with it for data reasons - user(Type)(Name)  example userTxtName
//if it's for the ui - ui(Type)(Name) example uiTxtName (this includes activity changers like buttons - it only effects the UI, data is not stored)

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //todo - tree system for tags - figure out how to do them as autofill/bubbles

    //database managers
    private DatabaseHelper databaseHelper;

    //activity managers
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int viewBack_CONFIG_REQUEST = 2;
    private final int settingsBack_CONFIG_REQUEST = 3;

    //list managers
    private ListView uiListView;
    private RsrtListAdapter adapter;

    //search managers
    private SearchView uiSearchView;
    private String searchQuery = "";

    public double[] priceScale = new double[]{10000, 0, 0, 0, 0};

    //self-reference context
    private Context thisContext;

    //data trackers
    private boolean dataUpdated = false;
    private boolean dataDeleted = false;
    public String dataSortType = "ID";
    public String dataSortOrder = "DESC";

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

        //initialize searchView
        uiSearchView = findViewById(R.id.uiSearch);
        uiSearchView.setOnQueryTextListener(this);

        //update list of restaurants displayed
        updateList(searchQuery);

        //first init of settings
        if (databaseHelper.settingsExist()) {
            createSettings();
        }

        //onClick listeners for list objects to get into viewRestaurant activity
        uiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent viewIntent = new Intent(thisContext, ViewRestaurantActivity.class);

                int ID = adapter.getItem(position).getID();
                viewIntent.putExtra("ID", ID);

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
        startActivityForResult(settingsIntent, settingsBack_CONFIG_REQUEST);
    }

    //result
    protected void onActivityResult(int requestCode, int resultCode, Intent submitData){
            super.onActivityResult(requestCode, resultCode, submitData);

                //on add activity -> click Submit
                if (requestCode == addSubmit_CONFIG_REQUEST) {
                    if (resultCode == Activity.RESULT_OK) {
//                        Toast.makeText(this, "switched over from add", Toast.LENGTH_LONG).show();

                        //updates ListView
                        updateList(searchQuery);
                    }

                    //on add activity -> clicked Back
                    if (resultCode == RESULT_CANCELED) {
//                        Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
                    }
                }

                //on view activity -> click Back
                if (requestCode == viewBack_CONFIG_REQUEST){
                    if (resultCode == Activity.RESULT_CANCELED) {

                        //checks to see if any data was modified while in the activity
                        dataUpdated = submitData.getExtras().getBoolean("dataUpdated");
                        dataDeleted = submitData.getExtras().getBoolean("dataDeleted");

                        if (dataUpdated || dataDeleted){
                            updateList(searchQuery);
                        }

//                        Toast.makeText(this, "switched over from view", Toast.LENGTH_LONG).show();

                    }
                }

                //on settings activity -> click Back
                if (requestCode == settingsBack_CONFIG_REQUEST){
            if (resultCode == Activity.RESULT_CANCELED) {

                updateList(searchQuery);

//                Toast.makeText(this, "switched over from settings and setting is now" + settingPriceNumber, Toast.LENGTH_LONG).show();

            }
        }

    }

    //new updates Field below Spinner, depending on pa
    protected void updateList(String searchText){
        databaseHelper = new DatabaseHelper(thisContext);
        List<Restaurant> restaurantList = databaseHelper.getAllRestaurants(dataSortType, dataSortOrder, searchText);

        uiListView = findViewById(R.id.uiListRsrt);
        TextView uiTxtEmpty = findViewById(R.id.uiTxtEmpty);

        //checks if list is empty
        if (restaurantList.isEmpty()) {
            Toast.makeText(MainActivity.this,"empty", Toast.LENGTH_LONG).show();
            uiTxtEmpty.setVisibility(View.VISIBLE);

        } else {
            uiTxtEmpty.setVisibility(View.INVISIBLE);
            //populates listView from rsrtArrayList
            adapter = new RsrtListAdapter(this, R.layout.activity_restaurant_widget, restaurantList, priceScale);
            uiListView.setAdapter(adapter);

        }
    }

    //spinner manager - handled populating the spinner of sort by choices
    protected void populateSpinner(){
        String[] sortTypes = new String[]{"Recents", "Oldest", "Price: Low to High", "Price: High to Low", "Rating: Low to High", "Rating: High to Low", "Alphabetic A-Z", "Alphabetic Z-A"};

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
                        updateList(searchQuery); //updates list
                        break;
                    case 1:
                        dataSortType = "ID";
                        dataSortOrder = "ASC";
                        updateList(searchQuery); //updates list
                        break;
                    case 2:
                        dataSortType = "PRICE";
                        dataSortOrder = "ASC";
                        updateList(searchQuery); //updates list
                        break;
                    case 3:
                        dataSortType = "PRICE";
                        dataSortOrder = "DESC";
                        updateList(searchQuery); //updates list
                        break;
                    case 4:
                        dataSortOrder = "RATING";
                        dataSortOrder = "ASC";
                        updateList(searchQuery); //updates list
                        break;
                    case 5:
                        dataSortType = "RATING";
                        dataSortOrder = "DESC";
                        updateList(searchQuery); //updates list
                        break;
                    case 6:
                        dataSortType = "RestaurantName";
                        dataSortOrder = "ASC";
                        updateList(searchQuery);
                        break;
                    case 7:
                        dataSortType = "RestaurantName";
                        dataSortOrder = "DESC";
                        updateList(searchQuery);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void createSettings(){
        Settings switchPriceNumber = new Settings("PriceView", 0);

        //0 is price/$$ switch
        ArrayList<Settings> storedSettings = new ArrayList<>();
        storedSettings.add(switchPriceNumber);

        databaseHelper.createSettings(storedSettings);
    }

    protected void testRestaurant(){

        String restaurantName = "TEST RESTAURANT -- ADDED IN CODE";
        Double price = 10.10;
        Double rating = 20.20;
        String notes = "TEST NOTES -- ADDED IN CODE";
        String[] tag_names = {"CodeTag1", "CodeTag2"};

        databaseHelper.createRestaurant(restaurantName, price, rating, notes, tag_names);
    }

    @Override
    public boolean onQueryTextSubmit(String searchText) {
        if (searchText.length() >= 1) {
            searchQuery = " WHERE RestaurantName LIKE \"%" + searchText + "%\"";
        }

        else { searchQuery = ""; }

        updateList(searchQuery);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        if (searchText.length() >= 1) {
            searchQuery = " WHERE RestaurantName LIKE \"%" + searchText + "%\"";
        }

        else { searchQuery = ""; }

        updateList(searchQuery);
        return false;
    }
}
