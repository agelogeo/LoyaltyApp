package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ManageCouponsActivity extends AppCompatActivity {
    private EditText nameView,requiredView;
    private Button create_btn,save_btn,delete_btn;
    ListView listView ;
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_coupons);

        nameView = (EditText) findViewById(R.id.coupon_name_view);
        requiredView = (EditText) findViewById(R.id.coupon_required_view);
        create_btn = (Button) findViewById(R.id.create_coupon_btn);
        save_btn = (Button) findViewById(R.id.save_changes_btn);
        delete_btn = (Button) findViewById(R.id.delete_coupon_btn);

        listView = (ListView) findViewById(R.id.coupons_list_view);
        new AttemptGetCoupons().execute();

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

    class AttemptGetCoupons extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageCouponsActivity.this);
            pDialog.setMessage(getString(R.string.creatingCustomer));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag

            Log.d("request!", "starting");

            JSONObject json = jsonParser.makeHttpRequest(
                    getString(R.string.GET_COUPONS_URL), "GET", params);
            System.out.println(getString(R.string.GET_COUPONS_URL));
            System.out.println(params);
            System.out.println(json.toString());
            // checking  log for json response
            //Log.d("Login attempt", json.toString());

            return json.toString();



        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(final String message) {
            ArrayList<Coupon> adapterList = new ArrayList<Coupon>();
            String toast_message= null;
            pDialog.dismiss();



            try {
                JSONObject output = new JSONObject(message);
                JSONArray result = output.getJSONArray("results");
                if (result.length() > 0) {
                    Log.d("Get Coupons OK!", result.toString());


                    for (int j = 0; j < result.length(); j++) {
                        Coupon tempItem = new Coupon();
                        JSONObject indicator = result.getJSONObject(j);
                        tempItem.setId(indicator.getInt("id"));
                        tempItem.setName(indicator.getString("name"));
                        tempItem.setRequired_stamps(indicator.getInt("required_stamps"));
                        adapterList.add(tempItem);
                    }

                    CouponAdapter myAdapter = new CouponAdapter(ManageCouponsActivity.this, adapterList);
                    listView.setAdapter(myAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            /*
                            JSONObject jsonResult = null;
                            try {
                                jsonResult = new JSONObject(message);
                                JSONArray results = (JSONArray) jsonResult.get("results");
                                JSONObject result = results.getJSONObject(adapterList.get(position).getResult_indicator());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            */
                        }
                    });
                } else if(result.length()==0){
                    toast_message = "No Coupons available.";


                }else{
                    toast_message =getString(R.string.barcodeExists);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (message != null){
                Toast.makeText(ManageCouponsActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        }


    }
}