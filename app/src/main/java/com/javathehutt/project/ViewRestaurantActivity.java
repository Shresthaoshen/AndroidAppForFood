package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRestaurantActivity extends AppCompatActivity {

    private final int editUpdate_CONFIG_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);



    }

    public void clickItem_Edit (View view){
        Intent modifyIntent = new Intent(this, EditRestaurantActivity.class);
        startActivityForResult(modifyIntent, editUpdate_CONFIG_REQUEST);
    }

    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent submitData) {
        super.onActivityResult(requestCode, resultCode, submitData);

        //on edit -> click Update
        if (requestCode == editUpdate_CONFIG_REQUEST) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "switched over from edit", Toast.LENGTH_LONG).show();
            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "switched over from back", Toast.LENGTH_LONG).show();
            }

        }
    }

}
