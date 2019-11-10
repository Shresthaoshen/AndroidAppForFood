package com.javathehutt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;

public class DatabaseHelper extends SQLiteOpenHelper {

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

    //add Restaurant entry to SQLite database
    public boolean insertData(String restaurantName, Double price, Double rating, String notes) {
        //Database constructor
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //First value in brackets is column name, second is value of data
        //Dont need one for ID because it's auto-incremented and generated
        contentValues.put(NAME, restaurantName);
        contentValues.put(PRICE, price);
        contentValues.put(RATING, rating);
        contentValues.put(NOTES, notes);

        //First argument table name, second is null, third is content values
        long result = database.insert(TABLE_RESTAURANT, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //update an SQLite entry
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
        return true;

    }

    //retrieve all from sql
    public Cursor getAllData(String dataSortType, String dataSortOrder) {
        SQLiteDatabase database = this.getWritableDatabase();
        //Cursor data = database.rawQuery("select * from " + TABLE_NAME + " ORDER BY ID DESC",null);
        Cursor data = database.rawQuery("select * from " + TABLE_RESTAURANT + " ORDER BY " + dataSortType + " " + dataSortOrder,null); //ok - the issue? order by name/alphabet doesn't work. don't know why, it just doesn't

        return data;

    }

    //delete data from sql
    public boolean deleteData(String id){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_RESTAURANT, "ID = ?", new String[] { id }) != 0;

    }

}
