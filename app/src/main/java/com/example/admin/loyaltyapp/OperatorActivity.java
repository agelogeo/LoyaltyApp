package com.example.admin.loyaltyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class OperatorActivity extends AppCompatActivity{
    private TextView welcomeView;
    private Button check_barcode_btn,new_operator_btn,new_customer_btn,delete_customer_btn,delete_operator_btn,db_btn;
    private static int ADMIN_ACCESS_LEVEL = 1;
    private Operator user = new Operator() ;
    private ImageView qr_reader;
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener okListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OperatorActivity.super.onBackPressed();
                    }
                };

        new AlertDialog.Builder(OperatorActivity.this)
                .setMessage(R.string.ExitMessageAlert)
                .setPositiveButton(R.string.yes,okListener)
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        check_barcode_btn = (Button) findViewById(R.id.check_barcode_btn);
        new_operator_btn = (Button) findViewById(R.id.new_operator_btn);
        new_customer_btn = (Button) findViewById(R.id.new_customer_btn);
        delete_customer_btn = (Button) findViewById(R.id.delete_customer_btn);
        delete_operator_btn = (Button) findViewById(R.id.delete_operator_btn);
        db_btn = (Button) findViewById(R.id.db_btn);

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
                new_operator_btn.setVisibility(View.GONE);
                delete_operator_btn.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new_operator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,OperatorCreation.class);
                startActivity(i);
            }
        });

        new_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,CustomerCreation.class);
                i.putExtra("operator",true);
                startActivity(i);
            }
        });

        delete_operator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,OperatorDeletion.class);
                startActivity(i);
            }
        });

        delete_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,CustomerDeletion.class);
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
    }
}
