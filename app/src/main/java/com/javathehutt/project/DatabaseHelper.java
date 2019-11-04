package com.javathehutt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database information (JTH = JavaTheHutt)
    public static final String DB_NAME = "JTH_RESTAURANT.DB";
    //data Table Name
    public static final String TABLE_NAME = "RESTAURANT_TABLE";

    //data table columns
    public static final String ID = "ID";
    public static final String NAME = "RestaurantName";
    public static final String PRICE = "Price";
    public static final String RATING = "Rating";
    public static final String NOTES = "Notes";
    public static final String TAGS = "Tags";


    //database version
    static final int DB_VERSION = 1;

    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+ " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME+" TEXT,"+ PRICE+" TEXT ,"+ RATING+" TEXT ,"+ NOTES+" TEXT ,"+ TAGS+" TEXT);";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE);
    }

    //called when scheme version we need != our current one
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    //add entry to SQLite database
    public boolean insertData(String restaurantName, String price, String rating, String notes, String tags) {
        //Database constructor
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //First value in brackets is column name, second is value of data
        //Dont need one for ID because it's auto-incremented and generated
        contentValues.put(NAME, restaurantName);
        contentValues.put(PRICE, price);
        contentValues.put(RATING, rating);
        contentValues.put(NOTES, notes);
        contentValues.put(TAGS, tags);

        //First argument table name, second is null, third is content values
        long result = database.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //update an SQLite entry
    public boolean updateData(String id, String restaurantName, String price, String rating, String notes, String tags) {
        //Database constructor
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //First value in brackets is column name, second is value of data
        contentValues.put(ID, id);
        contentValues.put(NAME, restaurantName);
        contentValues.put(PRICE, price);
        contentValues.put(RATING, rating);
        contentValues.put(NOTES, notes);
        contentValues.put(TAGS, tags);

        //first argument is table name, second is content values (data), third is whereclause, fourth is where
        database.update(TABLE_NAME, contentValues, "ID = ?", new String[]{ id });
        return true;

    }

    //retrieve all from sql
    public Cursor getAllData(String dataSortType, String dataSortOrder) {
        SQLiteDatabase database = this.getWritableDatabase();
        //Cursor data = database.rawQuery("select * from " + TABLE_NAME + " ORDER BY ID DESC",null);
        //Cursor data = database.rawQuery("select * from " + TABLE_NAME + " ORDER BY " + dataSortType + " " + dataSortOrder,null);
        Cursor data = database.rawQuery("select * from " + TABLE_NAME + " ORDER BY NAME DESC",null);

        return data;

    }

    //delete data from sql
    public boolean deleteData(String id){
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(TABLE_NAME, "ID = ?", new String[] { id }) != 0;

    }

}
