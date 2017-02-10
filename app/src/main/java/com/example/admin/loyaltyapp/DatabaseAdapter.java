package com.example.admin.loyaltyapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10/2/2017.
 */

class DatabaseAdapter extends ArrayAdapter<Customer> {

    public DatabaseAdapter(Context context, ArrayList<Customer> Listrowdata) {
        super(context, 0, Listrowdata);
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Customer listitem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_database_row, parent, false);
        }

        ConstraintLayout header = (ConstraintLayout) convertView.findViewById(R.id.listview_db_layout);
        TextView name = (TextView) header.findViewById(R.id.db_row_name);
        TextView phone = (TextView) header.findViewById(R.id.db_row_phone);
        TextView barcode = (TextView) header.findViewById(R.id.db_row_barcode);

        TextView stamps = (TextView) header.findViewById(R.id.db_row_stamps);
        TextView coupons_used = (TextView) header.findViewById(R.id.db_row_coupons_used);
        TextView last_visit = (TextView) header.findViewById(R.id.db_row_last_visit);
        TextView visits = (TextView) header.findViewById(R.id.db_row_visits);

        name.setTypeface(null,Typeface.BOLD);

        if (listitem != null) {
            name.setText(listitem.getName()+" "+listitem.getSurname());
            phone.setText(String.valueOf(listitem.getPhone()));
            barcode.setText(String.valueOf(listitem.getBarcode()));

            stamps.setText(listitem.getStamps()+" Stamps");
            coupons_used.setText(listitem.getCoupons_used()+" Coupons Used");
            last_visit.setText("Last Visit : "+listitem.getLast_visit());
            visits.setText(String.valueOf(listitem.getVisits()));
        }

        // Return the completed view to render on screen
        return convertView;
    }

    public static void ViewHolder () {

    }
}
