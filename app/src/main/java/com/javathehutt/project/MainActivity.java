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

    //activity requests
    private final int addSubmit_CONFIG_REQUEST = 1;
    private final int modifySubmit_CONFIG_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            }

    public void clickItem_Edit (View view){
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

    protected void onEditResult(int requestCode, int resultCode, Intent submitData){
        if (requestCode == addSubmit_CONFIG_REQUEST) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "switched over", Toast.LENGTH_LONG).show();

            }

        }
    }

    protected void onBackResult(int requestCode, int resultCode, Intent backData){

    }


}
