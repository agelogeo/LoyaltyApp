package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomerActivity extends AppCompatActivity {
    private TextView welcomeView,barcodeView;
    ImageView qrCodeImageview;
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
            GenerateBarCode(json.getString("barcode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GenerateBarCode(final String barcodeID) {
        qrCodeImageview=(ImageView) findViewById(R.id.custom_activity_barcode_view);
        new AsyncTask<Void,Void,Bitmap>(){
            @Override
            protected Bitmap doInBackground(Void... voids) {
                URL url = null;
                Bitmap bmp = null;
                try {
                    url = new URL("https://chart.googleapis.com/chart?cht=qr&chl="+barcodeID+"&chs=500x500");
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap aVoid) {
                qrCodeImageview.setImageBitmap(aVoid);
            }
        }.execute();
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
