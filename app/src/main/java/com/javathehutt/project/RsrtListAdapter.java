package com.javathehutt.project;

import android.content.Context;
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
        String uiRating = getItem(cPosition).getRating() + "";
        String uiPrice = getItem(cPosition).getPrice();

        //casting numbers to numbers
        double decimalPrice = Double.parseDouble(uiPrice);
        double decimalRating = Double.parseDouble(uiPrice);

        //format numbers
        String formatPrice  = String.format("%.02f", decimalPrice);
        int intRating = (int) Math.round(decimalRating);

        //place information on the widget layout
        LayoutInflater inflater = LayoutInflater.from(context);
        cConvertView = inflater.inflate(resource, cParent, false);

        //assign information to widget
        TextView editName = (TextView) cConvertView.findViewById(R.id.uiTxtTitleLabel);
        TextView editRating = (TextView) cConvertView.findViewById(R.id.uiTxtRatingLabel);
        TextView editPrice = (TextView) cConvertView.findViewById(R.id.uiTxtPriceLabel);

        ProgressBar barRating = (ProgressBar) cConvertView.findViewById(R.id.uiBarRating);

        editName.setText(uiName);
        editRating.setText(uiRating);
        editPrice.setText("$" + formatPrice);

        //set rating bar amount
        barRating.setProgress(intRating);

        return cConvertView;
    }

}
