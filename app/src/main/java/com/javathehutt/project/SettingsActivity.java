package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    //0 is price/$$ switch
    private ArrayList<Settings> storedSettings;

    //price handlers
    double[] priceScale;

    //UI setting elements
    Switch userSwitchPriceNumber;

    //UI elements
    TextView uiPriceHint;

    //database managers
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initialize UI setting elements
        userSwitchPriceNumber = findViewById(R.id.userSwitchPriceNumbers);

        //initialize UI elements
        uiPriceHint = findViewById(R.id.uiTxtDescPriceHint);

        //get database
        databaseHelper = new DatabaseHelper(this);

        updateSwitches();

        //set price information so people know the scale
        priceScale = databaseHelper.getPriceList();
        uiPriceHint.setText("$ = $" + (int) Math.round(priceScale[0]) + ".00 || $$ = $" + (int) Math.round(priceScale[1])
                + ".00 || $$$ = $" + (int) Math.round(priceScale[2]) + ".00 || $$$$ = $" + (int) Math.round(priceScale[3])
                + ".00 || $$$$$ = $" + (int) Math.round(priceScale[4]) + ".00");

        //sets check listeners for the switches
        checkListeners();
    }

    //onClick back - ends settings activity,
    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    //updates switches with stored data
    public void updateSwitches() {
        storedSettings = databaseHelper.getSettings();
        userSwitchPriceNumber.setChecked(1 <= storedSettings.get(0).getValue());
    }

    public void clickDelete (View view){
        Boolean dateDeleted = databaseHelper.deleteFullDatabase();

        if (dateDeleted){
            Toast.makeText(this, "Database Deleted", Toast.LENGTH_LONG).show();
        }
    }

    //saves data when switched
    public void checkListeners (){
        //price vs. $$$s
        userSwitchPriceNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                storedSettings.get(0).setValue(isChecked? 1 : 0);
                databaseHelper.updateSettings(storedSettings);
            }
        });
    }
}
