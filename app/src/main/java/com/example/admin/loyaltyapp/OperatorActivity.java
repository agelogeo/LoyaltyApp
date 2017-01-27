package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


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
                Intent ii = new Intent(OperatorActivity.this,CheckBarcodeActivity.class);
                ii.putExtra("barcode",barcode_value.getText().toString());
                startActivity(ii);
            }
        });

        manage_coupons_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OperatorActivity.this,ManageCouponsActivity.class);
                startActivity(i);
            }
        });
    }
}
