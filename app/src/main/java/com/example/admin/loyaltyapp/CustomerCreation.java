package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomerCreation extends AppCompatActivity {
    private EditText first_name,last_name,phone;
    private Button customer_create_btn;
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    private boolean isOperatorTheCreator ;

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

        Intent incoming = getIntent();
        isOperatorTheCreator = incoming.getBooleanExtra("operator",false);

        customer_create_btn = (Button) findViewById(R.id.customer_create_btn);


        customer_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(EditText et:TextList){
                    if(et.getText().length()==0){
                        Toast.makeText(CustomerCreation.this,getString(R.string.enterValuesToAllFields),Toast.LENGTH_LONG).show();
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
        ImageView qrCodeImageview ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CustomerCreation.this);
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

            JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_CREATION_URL), "GET", params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_CREATION_URL));
            System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                return json.toString();



        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(final String message) {
            int success;
            JSONObject json ;
            final LinearLayout profile_layout = (LinearLayout) findViewById(R.id.customer_profile_layout);
            final LinearLayout fields_layout = (LinearLayout) findViewById(R.id.fields_layout);
            String toast_message= null;
            try {
                json = new JSONObject(message);
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Login!", json.toString());
                    toast_message = getString(R.string.customerCreated);
                    profile_layout.setVisibility(View.VISIBLE);
                    TextView profile_name = (TextView) profile_layout.findViewById(R.id.profile_name_Text);
                    TextView profile_barcode_Text = (TextView) profile_layout.findViewById(R.id.profile_barcode_Text);
                    qrCodeImageview = (ImageView) profile_layout.findViewById(R.id.profile_barcode);
                    profile_name.setText(profile_name.getText()+" "+json.getString("name")+" "+json.getString("surname"));
                    profile_barcode_Text.setText(profile_barcode_Text.getText()+" "+json.getString("barcode"));
                    GenerateBarCode(json.getString("barcode"));
                    fields_layout.setVisibility(View.GONE);

                    if(isOperatorTheCreator){
                        Button jumpToCheckBarcode = (Button) findViewById(R.id.jumpToCheckBarcode_btn);
                        jumpToCheckBarcode.setVisibility(View.VISIBLE);
                        jumpToCheckBarcode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //JUMP TO CHECK BARCODE ACTIVITY PARSING BARCODE
                            }
                        });
                    }else{
                        Button jumpToLogin = (Button) findViewById(R.id.jumpToLogin_btn);
                        jumpToLogin.setVisibility(View.VISIBLE);
                        jumpToLogin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i5 = new Intent(CustomerCreation.this,CustomerActivity.class);
                                i5.putExtra("jsonResponse",message);
                                finish();
                                startActivity(i5);
                            }
                        });
                    }
                }else{
                    toast_message =getString(R.string.barcodeExists);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
            if (message != null){
                Toast.makeText(CustomerCreation.this, toast_message, Toast.LENGTH_LONG).show();
            }
        }

        private void GenerateBarCode(final String barcodeID) {
                new AsyncTask<Void,Void,Bitmap>(){
                    @Override
                    protected Bitmap doInBackground(Void... voids) {
                        URL url = null;
                        Bitmap bmp = null;
                        try {
                            url = new URL("https://chart.googleapis.com/chart?cht=qr&chl="+barcodeID+"&chs=500x500");
                            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        return bmp;
                    }

                    @Override
                    protected void onPostExecute(Bitmap aVoid) {
                        qrCodeImageview.setImageBitmap(aVoid);
                    }
                }.execute();

        }
    }
}
