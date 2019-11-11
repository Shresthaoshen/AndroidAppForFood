package com.javathehutt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.util.Log;

import java.util.ArrayList;
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


    //Common Columns
    public static final String ID = "ID";

    //Restaurant Table Columns
    public static final String NAME = "RestaurantName";
    public static final String PRICE = "Price";
    public static final String RATING = "Rating";
    public static final String NOTES = "Notes";

    //Tag Table Columns
    public static final String TAG_NAME = "TagName";

    //Restaurant_Tag Table Columns
    public static final String RESTAURANT_ID = "RestaurantID";
    public static final String TAG_ID = "TagID";


    //Create Restaurant Table
    public static final String CREATE_RESTAURANT_TABLE = "CREATE TABLE "+ TABLE_RESTAURANT + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT,"+ PRICE + " DECIMAL," + RATING + " DECIMAL," + NOTES + " TEXT);";

    //Create Tag Table
    public static final String CREATE_TAG_TABLE = "CREATE TABLE "+ TABLE_TAG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG_NAME + " TEXT);";

    //Create Restaurant_Tag Table
    public static final String CREATE_RESTAURANT_TAG_TABLE = "CREATE TABLE "+ TABLE_RESTAURANT_TAG + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + RESTAURANT_ID + " INTEGER,"+ TAG_ID + " INTEGER);";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_RESTAURANT_TABLE);
        database.execSQL(CREATE_TAG_TABLE);
        database.execSQL(CREATE_RESTAURANT_TAG_TABLE);
    }

    //called when scheme version we need != our current one
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_TAG);

        //create new tables
        onCreate(database);
    }

    //insert data read from AddRestaurantActivity to SQLite database
    public boolean createRestaurant(String restaurantName, Double price, Double rating, String notes, long[] tag_ids) {
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

        //assign tags to restaurant
        for (long tag_id : tag_ids) {
            createRestaurantTag(restaurant_id, tag_id);
        }

        //MAYBE CHANGE THIS??
        return (restaurant_id != -1);
    }


    //get single restaurant

    public Restaurant getRestaurant(long restaurant_id) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT + " WHERE "
                + RESTAURANT_ID + " = " + restaurant_id;

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
        String selectQuery = "select * from " + TABLE_RESTAURANT + " ORDER BY " + dataSortType + " " + dataSortOrder; //ok - the issue? order by name/alphabet doesn't work. don't know why, it just doesn't todo figure out why

        Log.e(LOG, selectQuery);

        SQLiteDatabase database = this.getReadableDatabase();
        //Cursor c = db.rawQuery(selectQuery, null);
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

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setID(c.getInt(c.getColumnIndex(ID)));
                restaurant.setName(c.getString(c.getColumnIndex(NAME)));
                restaurant.setPrice(c.getDouble(c.getColumnIndex(PRICE)));
                restaurant.setRating(c.getDouble(c.getColumnIndex(RATING)));
                restaurant.setNotes((c.getString(c.getColumnIndex(NOTES))));

                // adding to todo list
                restaurants.add(restaurant);
            } while (c.moveToNext());
        }

        return restaurants;
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

    //OLD METHOD TO GET ALL DATA AT ONCE- MAYBE WE SHOULDN'T USE??
    //retrieve all from sql
    public Cursor getAllData(String dataSortType, String dataSortOrder) {
        SQLiteDatabase database = this.getWritableDatabase();
        //Cursor data = database.rawQuery("select * from " + TABLE_NAME + " ORDER BY ID DESC",null);
        Cursor data = database.rawQuery("select * from " + TABLE_RESTAURANT + " ORDER BY " + dataSortType + " " + dataSortOrder,null); //ok - the issue? order by name/alphabet doesn't work. don't know why, it just doesn't

        return data;

    }

    //delete Restaurant from sql
    public boolean deleteData(String id){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_RESTAURANT, "ID = ?", new String[] { id }) != 0;

    }

    //create a tag in tag database

    public long createTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_NAME, tag.getTagName());

        // insert row
        long tag_id = db.insert(TABLE_TAG, null, values);

        return tag_id;
    }


    //get all tags in tag database

    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<Tag>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setID(c.getInt((c.getColumnIndex(ID))));
                t.setTagName(c.getString(c.getColumnIndex(TAG_NAME)));

                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }

    //update a tag

    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_NAME, tag.getTagName());

        // updating row
        return db.update(TABLE_TAG, values, ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }


    //Delete a tag from the database
    // Will delete all restaurants under the tag name if boolean is true

    public void deleteTag(Tag tag, boolean should_delete_all_tag_restaurants) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_restaurants) {
            // get all todos under this tag
            List<Restaurant> allTagRestaurants = getAllRestaurantsByTag(tag.getTagName());

            // delete all todos
            for (Restaurant restaurant : allTagRestaurants) {
                // delete todo
                //needs to be a string for the delete method - not my choice
                deleteData(restaurant.getID() + "");
            }
        }

        // now delete the tag
        db.delete(TABLE_TAG, ID+ " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }



    //Assigns a restaurant under a tag name by ids. 11

    public long createRestaurantTag(long todo_id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RESTAURANT_ID, todo_id);
        values.put(TAG_ID, tag_id);

        long id = db.insert(TABLE_RESTAURANT_TAG, null, values);

        return id;
    }



    //remove tag assigned to a restaurant. NOT FINISHED

//    public void deleteRestaurantTag(long id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.delete(TABLE_TAG, ID + " = ?",
//                new String[] { String.valueOf(tag_id) });
//
//    }


    //update tag of a restaurant. 13

    public int updateRestaurantTag(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TAG_ID, tag_id);

        // updating row
        return db.update(TABLE_RESTAURANT, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void closeDataBase() {
        SQLiteDatabase database = this.getReadableDatabase();
        if (database != null && database.isOpen())
            database.close();
    }




}
