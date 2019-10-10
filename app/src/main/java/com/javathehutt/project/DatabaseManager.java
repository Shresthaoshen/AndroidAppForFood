package com.javathehutt.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context c){
        context = c;
    }

    //opens the database connection by calling getWritableDatabase()
    public DatabaseManager open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    //to close a database connection
    public void close(){
        databaseHelper.close();
    }

    //adds a new record into the database
    public void insert(Restaurant restaurant){
        ContentValues contentValues = new ContentValues();

        contentValues.put(databaseHelper.NAME, restaurant.getName());
        contentValues.put(databaseHelper.PRICE, restaurant.getPrice());
        contentValues.put(databaseHelper.RATING, restaurant.getRating());
        contentValues.put(databaseHelper.NOTES, restaurant.getNotes());
        contentValues.put(databaseHelper.TAGS, restaurant.getTags());

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    //fetches all records
    public Cursor fetch(){
        String[] columns = new String[] {
                DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PRICE,
                DatabaseHelper.RATING, DatabaseHelper.NOTES, DatabaseHelper.TAGS};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    //update a single record
    public int update(long _id, Restaurant restaurant){
        ContentValues contentValues = new ContentValues();

        contentValues.put(databaseHelper.NAME, restaurant.getName());
        contentValues.put(databaseHelper.PRICE, restaurant.getPrice());
        contentValues.put(databaseHelper.RATING, restaurant.getRating());
        contentValues.put(databaseHelper.NOTES, restaurant.getNotes());
        contentValues.put(databaseHelper.TAGS, restaurant.getTags());

        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);

        return i;

    }

    //delete a record
    public void delete(long _id){
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + " = " + _id, null);
    }

}
