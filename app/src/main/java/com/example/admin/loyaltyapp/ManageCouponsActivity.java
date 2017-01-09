package com.example.admin.loyaltyapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManageCouponsActivity extends AppCompatActivity {
    private EditText nameView,requiredView;
    private Button create_btn,save_btn,delete_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_coupons);

        nameView = (EditText) findViewById(R.id.coupon_name_view);
        requiredView = (EditText) findViewById(R.id.coupon_required_view);
        create_btn = (Button) findViewById(R.id.create_coupon_btn);
        save_btn = (Button) findViewById(R.id.save_changes_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameView.getText().length()==0 || requiredView.getText().length()==0){
                    Toast.makeText(ManageCouponsActivity.this,"Please fill up fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameView.getText().length()==0 || requiredView.getText().length()==0){
                    Toast.makeText(ManageCouponsActivity.this,"Please fill up fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameView.getText().length()==0 || requiredView.getText().length()==0){
                    Toast.makeText(ManageCouponsActivity.this,"Please fill up fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void Dialog(){
        DialogInterface.OnClickListener okListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManageCouponsActivity.super.onBackPressed();
                    }
                };

        new AlertDialog.Builder(ManageCouponsActivity.this)
                .setMessage(R.string.ExitMessageAlert)
                .setPositiveButton(R.string.yes,okListener)
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }
}
