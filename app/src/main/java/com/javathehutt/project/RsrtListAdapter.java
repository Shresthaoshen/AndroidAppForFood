package com.javathehutt.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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

//cName = constructorThingy
public class RsrtListAdapter extends ArrayAdapter<Restaurant> {

    private Context context;
    int resource;

    public RsrtListAdapter(@NonNull Context cContext, int cResource, @NonNull ArrayList<Restaurant> cObjects) {
        super(cContext, cResource, cObjects);
        context = cContext;
        resource = cResource;
    }

    @NonNull
    @Override
    public View getView(int cPosition, @Nullable View cConvertView, @NonNull ViewGroup cParent) {
        DecimalFormat priceFormat = new DecimalFormat("#.##");

        //get restaurant information that's displayed on the widget
        String uiName = getItem(cPosition).getName();
        String uiPrice = getItem(cPosition).getPrice();
        String uiRating = getItem(cPosition).getRating() + "";

        //casting numbers to numbers
        double decimalPrice = Double.parseDouble(uiPrice);
        double decimalRating = Double.parseDouble(uiRating) * 10;

        //format numbers
        String formatPrice  = String.format("%.02f", decimalPrice);
        int intRating = (int) Math.round(decimalRating);

        //place information on the widget layout
        LayoutInflater inflater = LayoutInflater.from(context);
        cConvertView = inflater.inflate(resource, cParent, false);

        //assign information to widget
        TextView editName = (TextView) cConvertView.findViewById(R.id.uiTxtTitleLabel);
        TextView editPrice = (TextView) cConvertView.findViewById(R.id.uiTxtPriceLabel);
        TextView editRating = (TextView) cConvertView.findViewById(R.id.uiTxtRatingLabel);

        ProgressBar barRating = (ProgressBar) cConvertView.findViewById(R.id.uiBarRating);

        editName.setText(uiName);
        editPrice.setText("$" + formatPrice);
        editRating.setText(uiRating);

        //set rating bar amount
        barRating.setProgress(intRating);

        //sets color for rating bar
        LayerDrawable layerDrawable = (LayerDrawable) barRating.getProgressDrawable();
        Drawable progressDrawable = layerDrawable.findDrawableByLayerId(android.R.id.progress);
        progressDrawable.setColorFilter(Color.parseColor(getColor(intRating)), PorterDuff.Mode.SRC_IN);

        return cConvertView;
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
