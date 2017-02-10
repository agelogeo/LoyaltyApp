package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CheckBarcodeActivity extends AppCompatActivity {
    private String barcode;
    private final Customer user = new Customer();
    private final List<NameValuePair> params = new ArrayList<>();
    private ProgressDialog pDialog;
    private ListView listView ;
    private final JSONParser jsonParser = new JSONParser();
    private ArrayList<Boolean> isEnough;
    private static final String TAG_SUCCESS = "success";
    private TextView id,name,phone,stamps,coupons,visits,last_visit;
    private Button add_stamp,remove_stamp;
    private final int value = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_barcode);
        id=(TextView) findViewById(R.id.checkBarcodeView);
        name=(TextView) findViewById(R.id.checkNameView);
        phone=(TextView) findViewById(R.id.checkPhoneView);
        stamps=(TextView) findViewById(R.id.checkStampsView);
        coupons=(TextView) findViewById(R.id.checkCouponsView);
        visits=(TextView) findViewById(R.id.checkVisitsView);
        last_visit=(TextView) findViewById(R.id.checkLastVisitView);

        listView = (ListView) findViewById(R.id.check_coupons_list);
        add_stamp = (Button) findViewById(R.id.add_stamp_btn);
        remove_stamp = (Button) findViewById(R.id.remove_stamp_btn);
        dialogStart();
        Intent ii = getIntent();
        barcode = ii.getStringExtra("barcode");
        new AttemptCheckBarcode().execute();
    }

    private class AttemptCheckBarcode extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... args) {
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username",barcode));

                Log.d("request!", "starting check barcode");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_LOGIN_URL), params);
                System.out.println(params);

                if(!json.has(TAG_SUCCESS)){
                    //Toast.makeText(CheckBarcodeActivity.this, "Invalid barcode/phone", Toast.LENGTH_SHORT).show();
                    finish();
                    pDialog.dismiss();
                }
                else{
                    success = json.getInt(TAG_SUCCESS);

                    System.out.println("TAG SUCCESS : "+ success);
                    if (success == 1) {
                        Log.d("Successfully Login!", json.toString());

                        return json.toString();
                    }else{
                        Toast.makeText(CheckBarcodeActivity.this, "Invalid barcode/phone", Toast.LENGTH_SHORT).show();

                        return null;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            if(message!=null) {
                try {
                    JSONObject json = new JSONObject(message);
                    user.setId(json.getInt("id"));
                    user.setName(json.getString("name"));
                    user.setSurname(json.getString("surname"));
                    user.setPhone(json.getString("phone"));
                    user.setBarcode(json.getString("barcode"));
                    user.setStamps(json.getInt("stamps"));
                    user.setCoupons_used(json.getInt("coupons_used"));
                    user.setVisits(json.getInt("visits"));
                    user.setLast_visit(json.getString("last_visit"));

                    id.setText(user.getBarcode());
                    name.setText(user.getName() +" "+ user.getSurname());
                    phone.setText(user.getPhone());
                    stamps.setText(String.valueOf(user.getStamps()));
                    coupons.setText(String.valueOf(user.getCoupons_used()));
                    visits.setText(String.valueOf(user.getVisits()));
                    last_visit.setText(user.getLast_visit());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new AttemptGetCoupons().execute();

                add_stamp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.clear();
                        params.add(new BasicNameValuePair("id",String.valueOf(user.getId())));
                        params.add(new BasicNameValuePair("operation","add"));
                        params.add(new BasicNameValuePair("value",String.valueOf(value)));
                        new AttemptAddStamp().execute();
                    }
                });

                remove_stamp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.clear();
                        params.add(new BasicNameValuePair("id",String.valueOf(user.getId())));
                        params.add(new BasicNameValuePair("operation","remove"));
                        params.add(new BasicNameValuePair("value",String.valueOf(value)));
                        new AttemptRemoveStamp().execute();
                    }
                });


            }

        }
    }

    private class AttemptGetCoupons extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... args) {

            Log.d("request!", "starting get coupons");

            JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.GET_COUPONS_URL), params);
            System.out.println(params);
            System.out.println(json.toString());

            return json.toString();



        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(final String message) {
            final ArrayList<Coupon> adapterList = new ArrayList<>();
            String toast_message= null;
            pDialog.dismiss();

            isEnough = new ArrayList<>();

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
                        if(user.getStamps()>=indicator.getInt("required_stamps") && indicator.getInt("required_stamps")>=0) {
                            tempItem.setOkay(true);
                            isEnough.add(true);
                        }else {
                            tempItem.setOkay(false);
                            isEnough.add(false);
                        }
                        tempItem.setManage(false);
                        adapterList.add(tempItem);
                    }

                    CouponAdapter myAdapter = new CouponAdapter(CheckBarcodeActivity.this, adapterList);
                    listView.setAdapter(myAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(CheckBarcodeActivity.this,"Ok click",Toast.LENGTH_SHORT).show();


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

            if (toast_message != null){
                Toast.makeText(CheckBarcodeActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        }


    }

    private class AttemptAddStamp extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogStart();
        }

        @Override
        protected String doInBackground(String... args) {

            Log.d("request!", "starting add stamp");
            JSONObject jsonString = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CHANGE_STAMP_URL), params);

            return jsonString.toString();



        }

        protected void onPostExecute(final String message) {
            String toast_message= null;
            pDialog.dismiss();

            isEnough = new ArrayList<>();

            try {
                JSONObject output = new JSONObject(message);

                if (output.getInt("success") == 1) {
                    Log.d("Add stamp OK", output.toString());
                    new AttemptCheckBarcode().execute();
                } else if(output.getInt("success")!=1){
                    toast_message = "Error on stamp change";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (toast_message != null){
                Toast.makeText(CheckBarcodeActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        }


    }

    private class AttemptRemoveStamp extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogStart();
        }

        @Override
        protected String doInBackground(String... args) {

            Log.d("request!", "starting");

            JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CHANGE_STAMP_URL), params);
            System.out.println(params);
            System.out.println(json.toString());

            return json.toString();



        }

        protected void onPostExecute(final String message) {
            String toast_message= null;
            pDialog.dismiss();

            isEnough = new ArrayList<>();

            try {
                JSONObject output = new JSONObject(message);

                if (output.getInt("success") == 1) {
                    Log.d("Coupon Change OK!", output.toString());
                    new AttemptCheckBarcode().execute();
                } else if(output.getInt("success")!=1){
                    toast_message = "Error on stamp change";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (toast_message != null){
                Toast.makeText(CheckBarcodeActivity.this, toast_message, Toast.LENGTH_LONG).show();
            }
        }


    }

    private void dialogStart(){
        pDialog = new ProgressDialog(CheckBarcodeActivity.this);
        pDialog.setMessage("Please wait..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
}
