package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


//from now own, fuck spelling - restaurant is going to be shortened to rsrt
public class MainActivity extends AppCompatActivity {

    //database setup
    private DatabaseManager databaseManager;
    private SimpleCursorAdapter adapter;
    final String[] dataFrom = new String[] {DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.PRICE,
            DatabaseHelper.RATING, DatabaseHelper.NOTES, DatabaseHelper.TAGS};
    final int[] dataTo = new int[] {R.id.id, R.id.name, R.id.price, R.id.rating, R.id.notes, R.id.tags};

    //UI setup
    private ListView listRsrt;

    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        Cursor cursor = databaseManager.fetch();

        listRsrt = (ListView) findViewById(R.id.lstRsrt);
        listRsrt.setEmptyView(findViewById(R.id.txtEmptyList));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_restaurant, cursor, dataFrom, dataTo, 0);
        adapter.notifyDataSetChanged();

        listRsrt.setAdapter(adapter);

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

    protected void onBackResult(int requestCode, int resultCode, Intent backData){

    }


}
