package com.example.admin.loyaltyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class CustomerActivity extends AppCompatActivity {
    private final Customer user = new Customer();
    private ImageView qrCodeImageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        TextView welcomeView = (TextView) findViewById(R.id.welcomeCustomerView);
        TextView barcodeView = (TextView) findViewById(R.id.barcodeView);

        Intent ii = getIntent();
        String jsonResponse = ii.getStringExtra("jsonResponse");
        System.out.println(jsonResponse);
        try {
            JSONObject json = new JSONObject(jsonResponse);

            user.setId(json.getInt("id"));
            user.setName(json.getString("name"));
            user.setSurname(json.getString("surname"));
            user.setPhone(json.getString("phone"));
            user.setBarcode(json.getString("barcode"));
            user.setStamps(json.getInt("stamps"));
            user.setCoupons_used(json.getInt("coupons_used"));
            user.setVisits(json.getInt("visits"));
            user.setLast_visit(json.getString("last_visit"));

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
                URL url;
                Bitmap bmp = null;
                try {
                    url = new URL("https://chart.googleapis.com/chart?cht=qr&chl="+barcodeID+"&chs=500x500");
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CustomerActivity.this);
        LayoutInflater inflater = CustomerActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.quit_dialog, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

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
                CustomerActivity.super.onBackPressed();
            }
        });

        alertDialog.show();
    }
}
