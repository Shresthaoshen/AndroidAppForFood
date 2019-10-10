package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


//from now own, fuck spelling - restaurant is going to be shortened to rsrt
//if the user interacts with it - user(Type)(Name)  example userTxtName
//if it's for the ui - ui(Type)(Name) example uiTxtName



public class MainActivity extends AppCompatActivity {

    //database setup
    private DatabaseManager databaseManager;
    private SimpleCursorAdapter adapter;
    final String[] dataFrom = new String[] {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PRICE,
            DatabaseHelper.RATING, DatabaseHelper.NOTES, DatabaseHelper.TAGS};
    final int[] dataTo = new int[] {R.id.rsrtName, R.id.rsrtPrice, R.id.rsrtRating};

    //UI setup
    private ListView uiListRsrt;

    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int modifySubmit_CONFIG_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Cursor cursor = databaseManager.fetch();

        databaseManager.insert(demoRsrt());

    }

    public void clickModify (View view){
        Intent modifyIntent = new Intent(this, EditRestaurantActivity.class);
        startActivityForResult(modifyIntent, modifySubmit_CONFIG_REQUEST);
    }


    public void clickAdd (View view){
        Intent addIntent = new Intent(this, AddRestaurantActivity.class);
        startActivityForResult(addIntent, addSubmit_CONFIG_REQUEST);
    }

    protected void onAddResult(int requestCode, int resultCode, Intent submitData){
        if (requestCode == addSubmit_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "switched over", Toast.LENGTH_LONG).show();

            }

        }
    }

    private Restaurant demoRsrt() {
        Restaurant newRsrt = new Restaurant("title");

        newRsrt.setRating(0.1);
        newRsrt.setPrice(3.4);
        newRsrt.setNotes("noteees");

        return newRsrt;
    }

}
