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

public class OperatorCreation extends AppCompatActivity {
    private EditText username,password,password_again,first_name,last_name,phone;
    private Switch new_admin_switch;
    private Button create_operator_btn;
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_creation);

        final ArrayList<EditText> TextList = new ArrayList<>();
        username = (EditText) findViewById(R.id.username_Text);
        password = (EditText) findViewById(R.id.password_Text);
        password_again = (EditText) findViewById(R.id.password_valid_Text);
        first_name = (EditText) findViewById(R.id.first_name_Text);
        last_name = (EditText) findViewById(R.id.last_name_Text);
        phone = (EditText) findViewById(R.id.phone_Text);
        TextList.add(username);
        TextList.add(password);
        TextList.add(password_again);
        TextList.add(first_name);
        TextList.add(last_name);
        TextList.add(phone);

        new_admin_switch = (Switch) findViewById(R.id.new_admin_switch);
        create_operator_btn = (Button) findViewById(R.id.create_operator_btn);

        create_operator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(EditText et:TextList){
                    if(et.getText().length()==0){
                        Toast.makeText(OperatorCreation.this,"Please enter values to all fields.",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                params.clear();
                if(password.getText().toString().equals(password_again.getText().toString())) {
                    params.add(new BasicNameValuePair("username", username.getText().toString()));
                    params.add(new BasicNameValuePair("password", password.getText().toString()));
                    params.add(new BasicNameValuePair("first_name", first_name.getText().toString()));
                    params.add(new BasicNameValuePair("last_name", last_name.getText().toString()));
                    params.add(new BasicNameValuePair("phone", phone.getText().toString()));
                    if (new_admin_switch.isChecked())
                        params.add(new BasicNameValuePair("access_level", "1"));
                    new AttemptCreateOperator().execute();
                }else{
                    Toast.makeText(OperatorCreation.this,"Check your password again.",Toast.LENGTH_LONG).show();
                    return;
                }



            }
        });



    }

    class AttemptCreateOperator extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OperatorCreation.this);
            pDialog.setMessage("Creating new operator..");
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
                        getString(R.string.OPERATOR_CREATION_URL), "GET", params);
                System.out.println(getString(R.string.OPERATOR_CREATION_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Login!", json.toString());
                    return "Operator has been created successfully.";
                }else{
                    return "Username or Phone already exists.";

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
                Toast.makeText(OperatorCreation.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
