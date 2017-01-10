package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerDeletion extends AppCompatActivity {

    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    private EditText delete_Text;
    private Button delete_btn;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_deletion);

        delete_btn = (Button) findViewById(R.id.customer_delete_btn);
        delete_Text = (EditText) findViewById(R.id.customer_delete_Text);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(delete_Text.getText().length()==0){
                    Toast.makeText(CustomerDeletion.this,getString(R.string.inactiveSwitchHint),Toast.LENGTH_SHORT).show();
                    return;
                }
                params.clear();
                params.add(new BasicNameValuePair("username",delete_Text.getText().toString()));
                new AttemptDeleteCustomer().execute();
            }
        });


    }

    class AttemptDeleteCustomer extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CustomerDeletion.this);
            pDialog.setMessage(getString(R.string.deletingCustomer));
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
                        getString(R.string.WEBSITE_URL+R.string.CUSTOMER_DELETION_URL), "GET", params);
                System.out.println(getString(R.string.WEBSITE_URL+R.string.CUSTOMER_DELETION_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Deletion!", json.toString());
                    return getString(R.string.customerDeleted);
                }else{
                    return getString(R.string.accountdoesnotexist);

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
                Toast.makeText(CustomerDeletion.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
