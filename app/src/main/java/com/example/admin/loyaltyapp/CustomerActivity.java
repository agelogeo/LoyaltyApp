package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomerActivity extends AppCompatActivity {
    private TextView welcomeView,barcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        welcomeView = (TextView) findViewById(R.id.welcomeCustomerView);
        barcodeView = (TextView) findViewById(R.id.barcodeView);

        Intent ii = getIntent();
        String jsonResponse = ii.getStringExtra("jsonResponse");
        System.out.println(jsonResponse);
        try {
            JSONObject json = new JSONObject(jsonResponse);
            welcomeView.setText(welcomeView.getText()+" "+json.getString("name")+" "+json.getString("surname"));
            barcodeView.setText(barcodeView.getText()+" "+json.getString("barcode"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener okListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomerActivity.super.onBackPressed();
                    }
                };

        new AlertDialog.Builder(CustomerActivity.this)
                .setMessage("Are you sure , you want to sign off?")
                .setPositiveButton("Yes",okListener)
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}
