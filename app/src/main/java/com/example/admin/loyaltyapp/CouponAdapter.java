package com.example.admin.loyaltyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10/1/2017.
 */

public class CouponAdapter extends ArrayAdapter<Coupon> {

    public CouponAdapter(Context context, ArrayList<Coupon> Listrowdata) {
        super(context, 0, Listrowdata);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Coupon listitem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_row, parent, false);
        }

        LinearLayout header = (LinearLayout) convertView.findViewById(R.id.header_layout);
        TextView name = (TextView) header.findViewById(R.id.row_name);
        TextView required = (TextView) header.findViewById(R.id.row_required);

        if (listitem != null) {
            name.setText(listitem.getName());
            required.setText(required.getText()+" "+listitem.getRequired_stamps());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
