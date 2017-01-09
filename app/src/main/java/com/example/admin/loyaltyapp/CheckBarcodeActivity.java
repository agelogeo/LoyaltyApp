package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckBarcodeActivity extends AppCompatActivity {
    private String barcode;
    private Customer user = new Customer();
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private TextView id,name,phone,stamps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_barcode);
        id=(TextView) findViewById(R.id.checkBarcodeView);
        name=(TextView) findViewById(R.id.checkNameView);
        phone=(TextView) findViewById(R.id.checkPhoneView);
        stamps=(TextView) findViewById(R.id.checkStampsView);


        Intent ii = getIntent();
        barcode = ii.getStringExtra("barcode");
        new AttemptCheckBarcode().execute();
    }

    class AttemptCheckBarcode extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CheckBarcodeActivity.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username",barcode));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        getString(R.string.CUSTOMER_LOGIN_URL), "GET", params);
                System.out.println(getString(R.string.CUSTOMER_LOGIN_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                    return json.toString();
                }else{
                    Toast.makeText(CheckBarcodeActivity.this, "Invalid barcode/phone", Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if(message!=null) {
                try {
                    JSONObject json = new JSONObject(message);
                    user.setName(json.getString("name"));
                    user.setSurname(json.getString("surname"));
                    user.setPhone(json.getString("phone"));
                    user.setBarcode(json.getString("barcode"));
                    user.setStamps(json.getInt("stamps"));
                    user.setCoupons_used(json.getInt("coupons_used"));
                    user.setVisits(json.getInt("visits"));
                    user.setLast_visit("last_visit");

                    id.setText(id.getText() + " " + user.getBarcode());
                    name.setText(name.getText() + " " + user.getName());
                    phone.setText(phone.getText() + " " + user.getPhone());
                    stamps.setText(stamps.getText() + " " + user.getStamps());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
