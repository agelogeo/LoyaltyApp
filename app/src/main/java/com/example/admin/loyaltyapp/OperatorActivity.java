package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class OperatorActivity extends AppCompatActivity {
    private TextView welcomeView;
    private Button check_barcode_btn,new_operator_btn,new_customer_btn,delete_customer_btn,delete_operator_btn,db_btn;
    private static int ADMIN_ACCESS_LEVEL = 1;

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
                .setMessage("Are you sure , you want to sign off?")
                .setPositiveButton("Yes",okListener)
                .setNegativeButton("No", null)
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

        Intent ii = getIntent();
        String jsonResponse = ii.getStringExtra("jsonResponse");
        System.out.println(jsonResponse);
        welcomeView=(TextView) findViewById(R.id.welcomeView);
        try {
            JSONObject json = new JSONObject(jsonResponse);
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


    }




}
