package com.example.admin.loyaltyapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


public class OperatorActivity extends BaseNavigationDrawer{
    private TextView welcomeView;
    private Button check_barcode_btn,manage_customers_btn, manage_operators_btn,db_btn,manage_coupons_btn;
    private EditText barcode_value;
    private static int ADMIN_ACCESS_LEVEL = 1;
    private Operator user = new Operator() ;
    private ImageView qr_reader;
    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OperatorActivity.this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = OperatorActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.quit_dialog, null);
        dialogBuilder.setView(dialogView);
        //dialogBuilder.setTitle("Edit Coupon");
        final AlertDialog alertDialog = dialogBuilder.create();
        // set the custom dialog components - text, image and button

        Button noButton = (Button) dialogView.findViewById(R.id.dialog_no_btn);
        Button yesButton = (Button) dialogView.findViewById(R.id.dialog_yes_btn);

        // if button is clicked, close the custom dialog
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                OperatorActivity.super.onBackPressed();
            }
        });

        alertDialog.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check_barcode_btn = (Button) findViewById(R.id.check_barcode_btn);
        manage_customers_btn = (Button) findViewById(R.id.manage_customers_btn);
        manage_operators_btn = (Button) findViewById(R.id.manage_operators_btn);
        manage_coupons_btn = (Button) findViewById(R.id.manage_coupons_btn);
        db_btn = (Button) findViewById(R.id.db_btn);
        barcode_value = (EditText) findViewById(R.id.barcode_value);
        qr_reader = (ImageView) findViewById(R.id.qr_scanner_View);

        Intent ii = getIntent();
        String jsonResponse = ii.getStringExtra("jsonResponse");


        System.out.println(jsonResponse);
        welcomeView=(TextView) findViewById(R.id.welcomeView);
        try {
            JSONObject json = new JSONObject(jsonResponse);
            user.setId(json.getInt("id"));
            user.setUsername(json.getString("username"));
            user.setPassword(json.getString("password"));
            user.setAccess_level(json.getInt("access_level"));
            user.setFirst_name(json.getString("first_name"));
            user.setLast_name(json.getString("last_name"));
            user.setPhone(json.getString("phone"));

            welcomeView.setText(welcomeView.getText()+" "+json.getString("first_name")+" "+json.getString("last_name"));
            if(json.getInt("access_level")!=ADMIN_ACCESS_LEVEL){
                db_btn.setVisibility(View.GONE);
                manage_coupons_btn.setVisibility(View.GONE);
                manage_operators_btn.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        manage_operators_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,ManageOperators.class);
                startActivity(i);
            }
        });

        manage_customers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,ManageCustomers.class);
                startActivity(i);
            }
        });

        qr_reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,BarcodeActivity.class);
                startActivity(i);
            }
        });

        check_barcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(barcode_value.getText().length()==0)
                    Toast.makeText(OperatorActivity.this,"Please fill the field above.",Toast.LENGTH_SHORT).show();
                else {
                    Intent ii = new Intent(OperatorActivity.this,CheckBarcodeActivity.class);
                    ii.putExtra("barcode", barcode_value.getText().toString());
                    startActivity(ii);
                }
            }
        });

        manage_coupons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,ManageCoupons.class);
                startActivity(i);
            }
        });
    }
}
