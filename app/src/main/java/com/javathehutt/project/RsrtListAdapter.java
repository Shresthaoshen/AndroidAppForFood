package com.javathehutt.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//cName = constructorThingy
public class RsrtListAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    int resource;

    //price scale manager
    private double[] priceScale;

    //database managers
    DatabaseHelper databaseHelper;

    public List<Restaurant> restaurantList;

    public RsrtListAdapter(@NonNull Context context, int resource, @NonNull List<Restaurant> restaurantList, double[] priceScale) {
        super(context, resource, restaurantList);

        this.context = context;
        this.resource = resource;
        this.priceScale = priceScale;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get database
        databaseHelper = new DatabaseHelper(context);

        //get restaurant information that's displayed on the widget
        String uiName = restaurantList.get(position).getName();
        Double uiPrice = restaurantList.get(position).getPrice();
        Double uiRating = restaurantList.get(position).getRating();

        //number formatting
        double decimalRating = uiRating * 10;

        //format numbers
        String formatPrice  = String.format("%.02f", uiPrice);
        int intRating = (int) Math.round(decimalRating);

        //place information on the widget layout
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        //assign information to widget
        TextView editName = (TextView) convertView.findViewById(R.id.uiTxtTitleLabel);
        TextView editPrice = (TextView) convertView.findViewById(R.id.uiTxtPriceLabel);
        TextView editRating = (TextView) convertView.findViewById(R.id.uiTxtRatingLabel);

        ProgressBar barRating = (ProgressBar) convertView.findViewById(R.id.uiBarRating);

        editName.setText(uiName);

        //PRICE SETTINGS
        priceScale = databaseHelper.getPriceList();

        //PRICE VIEW SETTINGS
        int settingsPriceNumber = databaseHelper.getSettings().get(0).getValue();

        if (settingsPriceNumber >= 1) {
            editPrice.setText("$" + formatPrice);
            editPrice.setTextSize(16);
        }

        if (settingsPriceNumber <= 0) {
            String priceText = "";
            for (int i = 0; i < priceScale.length; i++ ){
                if (uiPrice >= priceScale[i]){
                    priceText += "<b>$</b>";
                }
                if (uiPrice < priceScale[i]) {
                    priceText += "$";
                }
            }
            editPrice.setText(Html.fromHtml(priceText));
            editPrice.setTextSize(18);

        }

        //RATING SETTINGS
        editRating.setText(uiRating + "");

        //set rating bar amount
        barRating.setProgress(intRating);

        //sets color for rating bar
        LayerDrawable layerDrawable = (LayerDrawable) barRating.getProgressDrawable();
        Drawable progressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
        progressDrawable.setColorFilter(Color.parseColor(getColor(intRating)), PorterDuff.Mode.SRC_IN);


        return convertView;
    }

    //gets color for the rating bar
    private String getColor (int rating){
        String color = "#E02121";

        //red to green
        if (rating <= 10){ color = "#E02121"; }
        if (10 < rating && rating <= 20){ color = "#E63F1A"; }
        if (20 < rating && rating <= 30){ color = "#EC5E13"; }
        if (30 < rating && rating <= 40){ color = "#F27D0D"; }
        if (40 < rating && rating <= 50){ color = "#F89C06"; }
        if (50 < rating && rating <= 60){ color = "#FFBB00"; }
        if (60 < rating && rating <= 70){ color = "#BFBE08"; }
        if (70 < rating && rating <= 80){ color = "#7FC110"; }
        if (80 < rating && rating <= 90){ color = "#3FC418"; }
        if (90 < rating ){ color = "#00C721"; }

        return color;
    }


}
