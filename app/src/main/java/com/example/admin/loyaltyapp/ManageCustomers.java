package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class ManageCustomers extends AppCompatActivity {

    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    private EditText customer_edit_text;
    private TextView waitText ;
    private Button delete_btn;
    private boolean doubleWildCard = false;
    private ListView listView;
    private JSONArray JSONresponse;
    private Button new_customer_btn;
    private ArrayList<Customer> CustomersArray = new ArrayList<>();
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_customers);
        listView  = (ListView) findViewById(R.id.customers_listview);

        customer_edit_text = (EditText) findViewById(R.id.customer_edit_Text);
        waitText = (TextView) findViewById(R.id.customerWaitText);

        new_customer_btn = (Button) findViewById(R.id.new_customer_btn);
        new_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ManageCustomers.this,CustomerCreation.class);
                i.putExtra("operator",true);
                startActivity(i);
            }
        });

        customer_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String sString = charSequence.toString();
                waitText.setVisibility(View.VISIBLE);
                if(sString.length()!=0) {
                    params.clear();
                    params.add(new BasicNameValuePair("term", customer_edit_text.getText().toString()));
                    if(doubleWildCard)
                        params.add(new BasicNameValuePair("double","true"));
                    new AttemptSearchCustomer().execute();
                }else {
                    waitText.setVisibility(View.GONE);
                    listView.setAdapter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ListViewItemClickListener(listView);
            }
        });

    }

    public void ListViewItemClickListener(final ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               // EditCoupon = new Coupon(adapterList.get(position).getId(),adapterList.get(position).getName(),adapterList.get(position).getRequired_stamps());
                final Customer tempC = CustomersArray.get(position);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageCustomers.this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = ManageCustomers.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.edit_customer_dialog, null);
                dialogBuilder.setView(dialogView);
                //dialogBuilder.setTitle("Edit Customer");
                final AlertDialog alertDialog = dialogBuilder.create();

                final EditText edit_customer_name = (EditText) dialogView.findViewById(R.id.edit_customer_name);
                final EditText edit_customer_surname = (EditText) dialogView.findViewById(R.id.edit_customer_surname);
                final EditText edit_customer_phone = (EditText) dialogView.findViewById(R.id.edit_customer_phone);
                final EditText edit_customer_barcode = (EditText) dialogView.findViewById(R.id.edit_customer_barcode);
                final EditText edit_customer_stamps = (EditText) dialogView.findViewById(R.id.edit_customer_stamps);
                final EditText edit_customer_coupons_used = (EditText) dialogView.findViewById(R.id.edit_customer_coupons_used);
                final EditText edit_customer_visists = (EditText) dialogView.findViewById(R.id.edit_customer_visits);
                final EditText edit_customer_last_visit = (EditText) dialogView.findViewById(R.id.edit_customer_last_visit);

                try {
                    edit_customer_name.setText(JSONresponse.getJSONObject(position).getString("name"));
                    edit_customer_surname.setText(JSONresponse.getJSONObject(position).getString("surname"));
                    edit_customer_phone.setText(JSONresponse.getJSONObject(position).getString("phone"));
                    edit_customer_barcode.setText(JSONresponse.getJSONObject(position).getString("barcode"));
                    edit_customer_stamps.setText(JSONresponse.getJSONObject(position).getString("stamps"));
                    edit_customer_coupons_used.setText(JSONresponse.getJSONObject(position).getString("coupons_used"));
                    edit_customer_visists.setText(JSONresponse.getJSONObject(position).getString("visits"));
                    edit_customer_last_visit.setText(JSONresponse.getJSONObject(position).getString("last_visit"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // set the custom dialog components - text, image and button
                /*final EditText edit_name = (EditText) dialogView.findViewById(R.id.dialog_edit_name);
                edit_name.setText(adapterList.get(position).getName());
                final EditText edit_stamps = (EditText) dialogView.findViewById(R.id.dialog_edit_stamps);
                edit_stamps.setText(""+adapterList.get(position).getRequired_stamps());*/
                            /*ImageView image = (ImageView) dialog.findViewById(R.id.image);
                            image.setImageResource(R.drawable.ic_launcher);
                            */
                Button cancelButton = (Button) dialogView.findViewById(R.id.edit_customer_cancel_btn);
                Button deleteButton = (Button) dialogView.findViewById(R.id.edit_customer_delete_btn);
                Button saveButton = (Button) dialogView.findViewById(R.id.edit_customer_save_btn);

                // if button is clicked, close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.clear();
                        params.add(new BasicNameValuePair("id", String.valueOf(CustomersArray.get(position).getId())));
                        new AttemptDeleteCustomer().execute();
                        alertDialog.dismiss();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(     edit_customer_name.getText().toString().equals(tempC.getName()) &&
                                edit_customer_surname.getText().toString().equals(tempC.getSurname()) &&
                                edit_customer_phone.getText().toString().equals(tempC.getPhone()) &&
                                edit_customer_barcode.getText().toString().equals(tempC.getBarcode()) &&
                                edit_customer_stamps.getText().toString().equals(String.valueOf(tempC.getStamps())) &&
                                edit_customer_coupons_used.getText().toString().equals(String.valueOf(tempC.getCoupons_used())) &&
                                edit_customer_visists.getText().toString().equals(String.valueOf(tempC.getVisits())) &&
                                edit_customer_last_visit.getText().toString().equals(String.valueOf(tempC.getLast_visit()))
                                ){
                            Toast.makeText(ManageCustomers.this,"No need to save anything.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        params.clear();
                        params.add(new BasicNameValuePair("id",String.valueOf(tempC.getId())));
                        params.add(new BasicNameValuePair("name",edit_customer_name.getText().toString()));
                        params.add(new BasicNameValuePair("surname",edit_customer_surname.getText().toString()));
                        params.add(new BasicNameValuePair("barcode",edit_customer_barcode.getText().toString()));
                        params.add(new BasicNameValuePair("phone",edit_customer_phone.getText().toString()));
                        params.add(new BasicNameValuePair("stamps",edit_customer_stamps.getText().toString()));
                        params.add(new BasicNameValuePair("coupons_used",edit_customer_coupons_used.getText().toString()));
                        params.add(new BasicNameValuePair("visits",edit_customer_visists.getText().toString()));
                        params.add(new BasicNameValuePair("last_visit",edit_customer_last_visit.getText().toString()));
                        new ManageCustomers.AttemptSaveCustomer().execute();
                        alertDialog.dismiss();
                        listView.setAdapter(null);
                    }
                });

                alertDialog.show();
            }
        });
    }

    class AttemptSearchCustomer extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waitText.setText("Please wait..");
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            Log.d("request!", "starting");

            JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.SEARCH_CUSTOMER_URL), "GET", params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.SEARCH_CUSTOMER_URL));
            System.out.println(params);

            return json.toString();
        }
        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         * **/
        protected void onPostExecute(String message) {

            try {
                // parse the json result returned from the service
                JSONObject obj = new JSONObject(message);
                JSONArray jsonResult = obj.getJSONArray("results");
                JSONresponse = jsonResult;
                String[] values = new String[jsonResult.length()];
                CustomersArray.clear();
                for (int i = 0; i < jsonResult.length(); i++) {
                    String label = jsonResult.getJSONObject(i).getString("name")+" "+jsonResult.getJSONObject(i).getString("surname");
                    values[i] = label;
                    CustomersArray.add(new Customer(jsonResult.getJSONObject(i).getInt("id"),
                            jsonResult.getJSONObject(i).getString("name"),
                            jsonResult.getJSONObject(i).getString("surname"),
                            jsonResult.getJSONObject(i).getString("phone"),
                            jsonResult.getJSONObject(i).getString("barcode"),
                            jsonResult.getJSONObject(i).getInt("stamps"),
                            jsonResult.getJSONObject(i).getInt("coupons_used"),
                            jsonResult.getJSONObject(i).getInt("visits"),
                            jsonResult.getJSONObject(i).getString("last_visit")
                    ));

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ManageCustomers.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                listView.setAdapter(adapter);
                if(CustomersArray.isEmpty()){
                    waitText.setVisibility(View.VISIBLE);
                    waitText.setText("No matches.");
                }else{
                    waitText.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class AttemptDeleteCustomer extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageCustomers.this);
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

                JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_DELETION_URL), "GET", params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_DELETION_URL));
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
                Toast.makeText(ManageCustomers.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    class AttemptSaveCustomer extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageCustomers.this);
            pDialog.setMessage("Saving customer");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag

            Log.d("request!", "starting");

            JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.SAVE_CUSTOMER_URL), "GET", params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.SAVE_CUSTOMER_URL));
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
            /*final ArrayList<Coupon> adapterList = new ArrayList<Coupon>();
            String toast_message= null;*/
            pDialog.dismiss();
            params.clear();

        }


    }
}
