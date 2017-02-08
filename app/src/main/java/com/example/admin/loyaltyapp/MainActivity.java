package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private EditText user, pass;
    private ProgressDialog pDialog;
    private Switch oper_switch;
    private String username;
    private String password;
    private final JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout pass_layout = (LinearLayout) findViewById(R.id.pass_layout);
        user = (EditText)findViewById(R.id.login_username);
        pass = (EditText)findViewById(R.id.login_password);
        oper_switch = (Switch) findViewById(R.id.operator_switch);
        oper_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oper_switch.isChecked()){
                    pass_layout.setVisibility(View.VISIBLE);
                    user.setHint("Username or Phone");
                }else {
                    pass_layout.setVisibility(View.GONE);
                    user.setHint("Barcode or Phone");
                }

            }
        });

        ImageButton pass_view = (ImageButton) findViewById(R.id.show_pass_btn);
        pass_view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        Button bLogin = (Button) findViewById(R.id.login_btn);
        bLogin.setOnClickListener(this);

        Button signup = (Button) findViewById(R.id.register_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this,CustomerCreation.class);
                ii.putExtra("operator",false);
                startActivity(ii);
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (user.getText().length() == 0){
                    Toast.makeText(MainActivity.this, "Barcode or Phone", Toast.LENGTH_SHORT).show();
                }else{
                    username = user.getText().toString();
                    if (oper_switch.isChecked()) {
                        if (pass.getText().length() == 0) {
                            Toast.makeText(MainActivity.this,"Password", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        password = pass.getText().toString();
                        new AttemptLoginForOperator().execute();
                    } else {
                        new AttemptLoginForCustomer().execute();
                    }
                }
            default:
                break;
        }
    }



    private class AttemptLoginForOperator extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage(getString(R.string.attemptingLogin));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_LOGIN_URL), params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_LOGIN_URL));
                System.out.println(params);

                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                    Intent ii = new Intent(MainActivity.this,OperatorActivity.class);
                    ii.putExtra("jsonResponse",json.toString());
                    finish();
                    startActivity(ii);
                    return getString(R.string.successfullyLogin);
                }else{
                    return getString(R.string.invalidNameOrPass);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AttemptLoginForCustomer extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", username));

                Log.d("request!", "starting login for customer");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_LOGIN_URL), params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_LOGIN_URL));
                System.out.println(params);

                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                    Intent ii = new Intent(MainActivity.this,CustomerActivity.class);
                    ii.putExtra("jsonResponse",json.toString());
                    finish();
                    startActivity(ii);
                    return "Successfully Login!";
                }else{
                    return "Invalid barcode/phone";

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
