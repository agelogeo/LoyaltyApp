package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerCreation extends AppCompatActivity {
    private EditText first_name,last_name,phone;
    private Button customer_create_btn;
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_creation);

        final ArrayList<EditText> TextList = new ArrayList<>();
        first_name = (EditText) findViewById(R.id.customer_name_Text);
        last_name = (EditText) findViewById(R.id.customer_surname_Text);
        phone = (EditText) findViewById(R.id.customer_phone_Text);
        TextList.add(first_name);
        TextList.add(last_name);
        TextList.add(phone);

        customer_create_btn = (Button) findViewById(R.id.customer_create_btn);

        customer_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(EditText et:TextList){
                    if(et.getText().length()==0){
                        Toast.makeText(CustomerCreation.this,"Please enter values to all fields.",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                params.clear();
                params.add(new BasicNameValuePair("name", first_name.getText().toString()));
                params.add(new BasicNameValuePair("surname", last_name.getText().toString()));
                params.add(new BasicNameValuePair("phone", phone.getText().toString()));
                new AttemptCreateCustomer().execute();
            }
        });



    }

    class AttemptCreateCustomer extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CustomerCreation.this);
            pDialog.setMessage("Creating new customer..");
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



                Log.d("request!", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        getString(R.string.CUSTOMER_CREATION_URL), "GET", params);
                System.out.println(getString(R.string.CUSTOMER_CREATION_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Login!", json.toString());
                    return "Customer has been created successfully.";
                }else{
                    return "Barcode or Phone already exists.";

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
            if (message != null){
                Toast.makeText(CustomerCreation.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
