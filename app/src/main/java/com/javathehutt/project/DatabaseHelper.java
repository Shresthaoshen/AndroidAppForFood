package com.javathehutt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Logcat
    private static final String LOG = "DatabaseHelper";

    //database version
    static final int DB_VERSION = 1;

    //database name (JTH = JavaTheHutt)
    public static final String DB_NAME = "JTH_RESTAURANT.DB";

    //Table names
    public static final String TABLE_RESTAURANT = "RESTAURANT_TABLE";
    public static final String TABLE_TAG = "TAG_TABLE";
    public static final String TABLE_RESTAURANT_TAG = "RESTAURANT_TAG_TABLE";
    public static final String TABLE_SETTINGS = "SETTINGS_TABLE";

    //Common Columns
    public static final String ID = "ID";

    //Restaurant Table Column Names
    public static final String NAME = "RestaurantName";
    public static final String PRICE = "Price";
    public static final String RATING = "Rating";
    public static final String NOTES = "Notes";

    //Tag Table Column Names
    public static final String TAG_NAME = "TagName";

    //Settings Columns
    public static final String PRICE_VIEWS = "PriceView";

    //Restaurant_Tag Table Column Names
    //both integers
    public static final String RESTAURANT_ID = "RestaurantID";
    public static final String TAG_ID = "TagID";

    //Create Restaurant Table
    public static final String CREATE_RESTAURANT_TABLE = "CREATE TABLE "+ TABLE_RESTAURANT + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT,"+ PRICE + " DECIMAL," + RATING + " DECIMAL," + NOTES + " TEXT);";

    //Create Tag Table
    public static final String CREATE_TAG_TABLE = "CREATE TABLE "+ TABLE_TAG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG_NAME + " TEXT);";

    //Create Restaurant_Tag Table
    public static final String CREATE_RESTAURANT_TAG_TABLE = "CREATE TABLE " + TABLE_RESTAURANT_TAG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RESTAURANT_ID + " INTEGER,"+ TAG_ID + " INTEGER);";

    //Create a Settings Table
    public static final String CREATE_SETTINGS_TABLE = "CREATE TABLE " + TABLE_SETTINGS + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRICE_VIEWS + " INTEGER);";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_RESTAURANT_TABLE);
        database.execSQL(CREATE_TAG_TABLE);
        database.execSQL(CREATE_RESTAURANT_TAG_TABLE);
        database.execSQL(CREATE_SETTINGS_TABLE);
    }

    //called when scheme version we need != our current one
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_TAG);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);

        //create new tables
        onCreate(database);
    }

    // -------------- RESTAURANTS -------------- //

    //insert data read from AddRestaurantActivity to SQLite database
    public boolean createRestaurant(String restaurantName, Double price, Double rating, String notes, String[] tag_names) {

        boolean added = false;

        //Database constructor
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //First value in brackets is column name, second is value of data
        //Don't need one for ID because it's auto-incremented and generated
        contentValues.put(NAME, restaurantName);
        contentValues.put(PRICE, price);
        contentValues.put(RATING, rating);
        contentValues.put(NOTES, notes);

        //insert entry
        //First argument table name, second is null, third is content values
        long restaurant_id = database.insert(TABLE_RESTAURANT, null, contentValues);

        added=true;


        //assign restaurant-tag pairings
        //if tag already exists, use existing tag-id for pair calling createRestaurantTag
        //if tag doesn't exist, createTag then createRestaurantTag

        for (String tag_name : tag_names) {

            boolean tagExists = checkTagAlreadyExists(tag_name);

            if (tagExists == true) {
                createRestaurantTag(restaurant_id, getTagIDFromName(tag_name));
            } else {

                long tag_id = createTag(tag_name);
                createRestaurantTag(restaurant_id, tag_id);
            }
        }
        return added;
    }

    //get single restaurant
    public Restaurant getRestaurant(long restaurant_id) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + ID + " = " + restaurant_id;

        Log.e(LOG, selectQuery);

        Cursor data = database.rawQuery(selectQuery, null);

        if (data != null)
            data.moveToFirst();

        Restaurant restaurant = new Restaurant();
            restaurant.setID(data.getInt(data.getColumnIndex(ID)));
            restaurant.setName(data.getString(data.getColumnIndex(NAME)));
            restaurant.setPrice(data.getDouble(data.getColumnIndex(PRICE)));
            restaurant.setRating(data.getDouble(data.getColumnIndex(RATING)));
            restaurant.setNotes((data.getString(data.getColumnIndex(NOTES))));

        return restaurant;
    }

    //get all restaurants in a list
    public List<Restaurant> getAllRestaurants(String dataSortType, String dataSortOrder) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        //String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT + " ORDER BY " + dataSortType + " " + dataSortOrder; //ok - the issue? order by name/alphabet doesn't work. don't know why, it just doesn't todo figure out why

        Log.e(LOG, selectQuery);

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (data.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(data.getInt(data.getColumnIndex(ID)));
                restaurant.setName(data.getString(data.getColumnIndex(NAME)));
                restaurant.setPrice(data.getDouble(data.getColumnIndex(PRICE)));
                restaurant.setRating(data.getDouble(data.getColumnIndex(RATING)));
                restaurant.setNotes((data.getString(data.getColumnIndex(NOTES))));

                // adding to todo list
                restaurants.add(restaurant);
            } while (data.moveToNext());
        }

        return restaurants;
    }

    //get all restaurants belonging to a tag
    public List<Restaurant> getAllRestaurantsByTag(String tag_name) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " td, "
                + TABLE_TAG + " tg, " + TABLE_RESTAURANT_TAG + " tt WHERE tg."
                + TAG_NAME + " = '" + tag_name + "'" + " AND tg." + ID
                + " = " + "tt." + TAG_ID + " AND td." + ID + " = "
                + "tt." + RESTAURANT_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (data.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(data.getInt(data.getColumnIndex(ID)));
                restaurant.setName(data.getString(data.getColumnIndex(NAME)));
                restaurant.setPrice(data.getDouble(data.getColumnIndex(PRICE)));
                restaurant.setRating(data.getDouble(data.getColumnIndex(RATING)));
                restaurant.setNotes((data.getString(data.getColumnIndex(NOTES))));

                // adding to todo list
                restaurants.add(restaurant);
            } while (data.moveToNext());
        }

        return restaurants;
    }

    //Assigns a restaurant under a tag name by ids. 11
    public long createRestaurantTag(long todo_id, long tag_id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTAURANT_ID, todo_id);
        values.put(TAG_ID, tag_id);

        long id = database.insert(TABLE_RESTAURANT_TAG, null, values);

        return id;
    }

    //update Restaurant entry
    public boolean updateData(String id, String restaurantName, Double price, Double rating, String notes, String tags) {
        //Database constructor
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //First value in brackets is column name, second is value of data
        contentValues.put(ID, id);
        contentValues.put(NAME, restaurantName);
        contentValues.put(PRICE, price);
        contentValues.put(RATING, rating);
        contentValues.put(NOTES, notes);

        //first argument is table name, second is content values (data), third is whereclause, fourth is where
        database.update(TABLE_RESTAURANT, contentValues, "ID = ?", new String[]{ id });

        //SHOULD THIS BE BOOLEAN?
        return true;

    }

    //delete Restaurant from sql
    public boolean deleteData(String id){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_RESTAURANT, "ID = ?", new String[] { id }) != 0;

    }

    // -------------- TAGS -------------- //

    //create a tag in tag database
    public long createTag(String tagName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_NAME, tagName);

        // insert row
        long tag_id = db.insert(TABLE_TAG, null, values);

        return tag_id;
    }

    public boolean checkTagAlreadyExists(String tag_name) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TAG +" WHERE " + TAG_NAME + " =?";

        Cursor data = database.rawQuery(selectQuery, new String[]{tag_name});

        boolean tagExists = false;

        if (data.moveToFirst()){
            tagExists= true;

        }

        data.close();
        database.close();
        return tagExists;

    }

    //get Tag ID from Tag Name
    public long getTagIDFromName(String tag_name) {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TAG +" WHERE " + TAG_NAME + " =?";

        Cursor data = database.rawQuery(selectQuery, new String[]{tag_name});

        long result = -1;

        int idRow = data.getColumnIndex(ID);

        for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()){
            result = data.getLong(idRow);
        }

        data.close();
        database.close();

        return result;

    }

    //get all tags in tag database
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<Tag>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (data.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setID(data.getInt((data.getColumnIndex(ID))));
                t.setTagName(data.getString(data.getColumnIndex(TAG_NAME)));

                // adding to tags list
                tags.add(t);
            } while (data.moveToNext());
        }
        return tags;
    }

    //update a tag
    public int updateTag(Tag tag) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_NAME, tag.getTagName());

        // updating row
        return database.update(TABLE_TAG, values, ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }

    //Delete a tag from the database
    // Will delete all restaurants under the tag name if boolean is true
    public void deleteTag(Tag tag, boolean should_delete_all_tag_restaurants) {
        SQLiteDatabase database = this.getWritableDatabase();

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_restaurants) {
            // get all todo under this tag
            List<Restaurant> allTagRestaurants = getAllRestaurantsByTag(tag.getTagName());

            // delete all todos
            for (Restaurant restaurant : allTagRestaurants) {
                // delete todo
                //needs to be a string for the delete method - not my choice
                deleteData(restaurant.getID() + "");
            }
        }

        // now delete the tag
        database.delete(TABLE_TAG, ID+ " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }

    //remove tag assigned to a restaurant. NOT FINISHED todo
//    public void deleteRestaurantTag(long id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.delete(TABLE_TAG, ID + " = ?",
//                new String[] { String.valueOf(tag_id) });
//
//    }

    //update tag of a restaurant.
    public int updateRestaurantTag(long id, long tag_id) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_ID, tag_id);

        // updating row
        return database.update(TABLE_RESTAURANT, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    // -------------- SETTINGS -------------- //

    //build price scale
    private double[] buildPriceScale(){

        List<Double> priceList = new ArrayList<Double>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (data.moveToFirst()) {
            do {
                priceList.add(data.getDouble(data.getColumnIndex(PRICE)));
            } while (data.moveToNext());
        }

        //price trackers
        //array lines up with min/max prices - [0] is $, [5] is $$$$$
        double[] priceScale = new double[]{0, 0, 0, 0, 0};

        Collections.sort(priceList);

        //finds the average
        double priceAverage = 0;
        if (priceList.size() > 0){

            for (int i = 0; i < priceList.size(); i++) {
                priceAverage += priceList.get(i);
            }
            priceAverage = priceAverage/(priceList.size());


            //min ($)
            priceScale[0] = priceList.get(0);
            //max ($$$$$)
            priceScale[4] = priceList.get(priceList.size()-1);
            //average ($$$)
            priceScale[2] = priceAverage;
            //min average ($$)
            priceScale[1] = (priceAverage+priceScale[0])/2;
            //max average ($$$$)
            priceScale[3] = (priceAverage+priceScale[4])/2;
        }

        return priceScale;
    }

    //returns prices scale
    public double[] getPriceList(){
        return buildPriceScale();
    }

    //sets settings values (only should be called if there is no setting row - there should be only one row)
    public void createSettings(ArrayList<Settings> settingsData){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (int i = 0; i < settingsData.size(); i++) {
            values.put(settingsData.get(i).getKey(), settingsData.get(i).getValue());
        }

        database.insert(TABLE_SETTINGS, null, values); //todo keeps inserting - where? find aout (i shouldnt have 13 rows, just one)
    }

    //updates settings values
    public int updateSettings(ArrayList<Settings> settingsData){
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SETTINGS;

        Cursor data = database.rawQuery(selectQuery, null);

        if (data != null){
            data.moveToLast();
        }

        int id = (data.getPosition()) + 1;

        ContentValues values = new ContentValues();
        for (int i = 0; i < settingsData.size(); i++) {
            values.put(settingsData.get(i).getKey(), settingsData.get(i).getValue());
        }

        // updating
        return database.update(TABLE_SETTINGS, values, ID + " = ?",
                new String[] {String.valueOf(id)});
    }

    //returns settings values
    public ArrayList<Settings> getSettings(){
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SETTINGS;

        Log.e(LOG, selectQuery);

        Cursor data = database.rawQuery(selectQuery, null);

        if (data != null){
            data.moveToLast();
        }

        //build setting object
        Settings priceSetting = new Settings();
        priceSetting.setKey(PRICE_VIEWS);
        priceSetting.setValue(data.getInt(data.getColumnIndex(PRICE_VIEWS)));

        //add setting objects to settings list
        ArrayList<Settings> settingsData = new ArrayList<>();
            settingsData.add(priceSetting);

        return settingsData;
    }

    //returns if setting row has been created or not
    public boolean settingsExist(){
        SQLiteDatabase database = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(database, TABLE_SETTINGS);
        database.close();

        return (1 > count);
    }

    private int convertBoolToInt (Boolean convert){
        if (convert){ return 1;}
        return 0;
    }

    public void closeDataBase() {
        SQLiteDatabase database = this.getReadableDatabase();
        if (database != null && database.isOpen())
            database.close();
    }




}
