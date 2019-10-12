package com.javathehutt.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        //get restaurant information
        String name = getItem(cPosition).getName();
        String rating = getItem(cPosition).getRating() + "";

        Double decimalPrice = getItem(cPosition).getPrice();
        String price  = String.format("%.02f", decimalPrice);

        LayoutInflater inflater = LayoutInflater.from(context);
        cConvertView = inflater.inflate(resource, cParent, false);

        TextView txtName = (TextView) cConvertView.findViewById(R.id.rsrtName);
        TextView txtRating = (TextView) cConvertView.findViewById(R.id.rsrtRating);
        TextView txtPrice = (TextView) cConvertView.findViewById(R.id.rsrtPrice);

        txtName.setText(name);
        txtRating.setText(rating);
        txtPrice.setText("$" + price);

        return cConvertView;
    }

    //testing some stuff
    public Restaurant getItemAtPosition(int position){
        return getItem(position);
    }
}
