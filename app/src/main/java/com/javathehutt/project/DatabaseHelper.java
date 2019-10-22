package com.javathehutt.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //database information (JTH = JavaTheHutt)
    public static final String DB_NAME = "JTH_RESTAURANT.DB";
    //data Table Name
    public static final String TABLE_NAME = "RESTAURANT_TABLE";

    //data table columns
    public static final String _ID = "ID";
    public static final String NAME = "Restaurant Name";
    public static final String PRICE = "Price";
    public static final String RATING = "Rating";
    public static final String NOTES = "Notes";
//    public static final String TAGS = "Tags";



    //database version
    static final int DB_VERSION = 1;

//    //creating table query
//    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID +
//            " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + PRICE + " REAL, " +
//            RATING + " REAL, " + NOTES + " TEXT, " + TAGS + " TEXT);";


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase database = this.getWritableDatabase();
    }

    //called when there's no database and the app needs one
    @Override
    public void onCreate(SQLiteDatabase database) {
//        database.execSQL(CREATE_TABLE);
        database.execSQL("create table " + TABLE_NAME +" (ID PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE INTEGER, RATING INTEGER, NOTES TEXT)");
    }

    //called when scheme version we need != our current one
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
