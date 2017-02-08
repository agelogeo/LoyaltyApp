package com.example.admin.loyaltyapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 10/1/2017.
 */

class CouponAdapter extends ArrayAdapter<Coupon> {

    public CouponAdapter(Context context, ArrayList<Coupon> Listrowdata) {
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
        Coupon listitem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_row, parent, false);
        }

        LinearLayout header = (LinearLayout) convertView.findViewById(R.id.header_layout);
        TextView name = (TextView) header.findViewById(R.id.row_name);
        TextView required = (TextView) header.findViewById(R.id.row_required);
        ImageView credit = (ImageView) header.findViewById(R.id.credit_card_icon);
        ImageView pen = (ImageView) header.findViewById(R.id.pen_icon);
        if (listitem != null) {
            name.setText(listitem.getName());
            String re = "Required : ";
            required.setText(re+" "+listitem.getRequired_stamps());
            if(!listitem.isOkay()) {
                name.setTextColor(Color.BLACK);
                required.setTextColor(Color.BLACK);
                credit.setColorFilter(Color.BLACK);
            }
            if(listitem.isManage())
                credit.setVisibility(View.GONE);
            else
                pen.setVisibility(View.GONE);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
