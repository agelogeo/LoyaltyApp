package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OtherActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener okListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OtherActivity.super.onBackPressed();
                    }
                };

        new AlertDialog.Builder(OtherActivity.this)
                .setMessage("Are you sure , you want to sign off?")
                .setPositiveButton("Yes",okListener)
                .setNegativeButton("No", null)
                .create()
                .show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }




}
