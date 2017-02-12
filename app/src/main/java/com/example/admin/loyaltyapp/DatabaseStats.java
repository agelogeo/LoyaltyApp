package com.example.admin.loyaltyapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseStats extends AppCompatActivity {
    private final ArrayList<Customer> CustomersArray = new ArrayList<>();
    private final List<NameValuePair> params = new ArrayList<>();
    private final JSONParser jsonParser = new JSONParser();
    private ListView db_list;
    private JSONArray JSONresponse;
    private TextView waitText;
    private ProgressDialog pDialog;
    private Spinner filter_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_stats);

        filter_spinner = (Spinner) findViewById(R.id.filter_entry);

        filter_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                params.clear();
                switch(filter_spinner.getSelectedItemPosition()){
                    case 0:
                        params.add(new BasicNameValuePair("filter","name"));
                        break;
                    case 1:
                        params.add(new BasicNameValuePair("filter","stamps"));
                        break;
                    case 2:
                        params.add(new BasicNameValuePair("filter","barcode"));
                        break;
                    case 3:
                        params.add(new BasicNameValuePair("filter","visits"));
                        break;
                    default:
                        params.add(new BasicNameValuePair("filter","default"));
                }

                db_list = (ListView) findViewById(R.id.db_list_view);
                waitText = (TextView) findViewById(R.id.db_waitText);
                new AttemptGetDB().execute();
                ListViewItemClickListener(db_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private class AttemptGetDB extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waitText.setVisibility(View.VISIBLE);
            waitText.setText("Please wait . . .");
            db_list.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... args) {
            Log.d("request!", "starting getting database");

            JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.DATABASE_STATS_URL),  params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.DATABASE_STATS_URL));
            System.out.println(params);

            return json.toString();
        }

        protected void onPostExecute(String message) {
            try {
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
                DatabaseAdapter myAdapter = new DatabaseAdapter(DatabaseStats.this, CustomersArray);
                db_list.setAdapter(myAdapter);
                db_list.setDividerHeight(5);
                /*
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DatabaseStats.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                db_list.setAdapter(adapter);
                */

                if(CustomersArray.isEmpty()){
                    db_list.setVisibility(View.GONE);
                    waitText.setVisibility(View.VISIBLE);
                    waitText.setText("No matches.");
                }else{
                    waitText.setVisibility(View.GONE);
                    db_list.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ListViewItemClickListener(final ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Customer tempC = CustomersArray.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DatabaseStats.this);
                LayoutInflater inflater = DatabaseStats.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.edit_customer_dialog, null);
                dialogBuilder.setView(dialogView);
                final AlertDialog EditCustomerDialog = dialogBuilder.create();

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

                Button cancelButton = (Button) dialogView.findViewById(R.id.edit_customer_cancel_btn);
                Button deleteButton = (Button) dialogView.findViewById(R.id.edit_customer_delete_btn);
                Button saveButton = (Button) dialogView.findViewById(R.id.edit_customer_save_btn);

                // if button is clicked, close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditCustomerDialog.dismiss();
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        params.clear();
                        params.add(new BasicNameValuePair("id", String.valueOf(CustomersArray.get(position).getId())));
                        new DatabaseStats.AttemptDeleteCustomer().execute();
                        EditCustomerDialog.dismiss();
                        CustomersArray.remove(position);
                        db_list.setAdapter(new DatabaseAdapter(DatabaseStats.this, CustomersArray));
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
                            Toast.makeText(DatabaseStats.this,"No need to save anything.",Toast.LENGTH_SHORT).show();
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
                        new DatabaseStats.AttemptSaveCustomer().execute();
                        EditCustomerDialog.dismiss();


                    }
                });

                EditCustomerDialog.show();
            }
        });
    }

    public class AttemptDeleteCustomer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DatabaseStats.this);
            pDialog.setMessage(getString(R.string.deletingCustomer));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;

            try {
                Log.d("request!", "starting delete customer");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_DELETION_URL), params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_DELETION_URL));
                System.out.println(params);

                success = json.getInt("success");
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

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(DatabaseStats.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AttemptSaveCustomer extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DatabaseStats.this);
            pDialog.setMessage("Saving customer");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            Log.d("request!", "starting");

            JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.SAVE_CUSTOMER_URL), params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.SAVE_CUSTOMER_URL));
            System.out.println(params);
            System.out.println(json.toString());

            return json.toString();



        }

        protected void onPostExecute(final String message) {
            params.clear();
            params.add(new BasicNameValuePair("filter","default"));
            new AttemptGetDB().execute();
            pDialog.dismiss();
            params.clear();

        }


    }
}
