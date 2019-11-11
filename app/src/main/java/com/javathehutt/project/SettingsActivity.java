package com.javathehutt.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    //false = display as $$s, true = display as $0.00
    private boolean settingPriceNumber = true;

    //price handlers
    double[] priceScale;

    //UI setting elements
    Switch userSwitchPriceNumber;

    //UI elements
    TextView uiPriceHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initialize UI setting elements
        userSwitchPriceNumber = findViewById(R.id.userSwitchPriceNumbers);

        //initialize UI elements
        uiPriceHint = findViewById(R.id.uiTxtDescPriceHint);

        updateSwitches();

        //set price information so people know the scale
        priceScale = getIntent().getDoubleArrayExtra("priceScale");
        uiPriceHint.setText("$ = $" + (int) Math.round(priceScale[0]) + ".00 || $$ = $" + (int) Math.round(priceScale[1])
                + ".00 || $$$ = $" + (int) Math.round(priceScale[2]) + ".00 || $$$$ = $" + (int) Math.round(priceScale[3])
                + ".00 || $$$$$ = $" + (int) Math.round(priceScale[4]) + ".00");

        //sets check listeners for the switches
        checkListeners();
    }

    //onClick back - ends settings activity, todo saves data
    public void clickBack (View view){
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("settingPriceNumber", settingPriceNumber);
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    //updates switches with stored data
    //todo figure out a way to check this - settings database?
    public void updateSwitches() {
        userSwitchPriceNumber.setChecked(settingPriceNumber);
        Toast.makeText(this, "$$$", Toast.LENGTH_LONG).show();
    }

    public void checkListeners (){
        //price vs. $$$s
        userSwitchPriceNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //todo this is where we'd store the information if i knew how to do that
                settingPriceNumber = isChecked;
            }
        });

    }
}
