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
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageOperators extends AppCompatActivity {
    private List<NameValuePair> params = new ArrayList<>();
    // Progress Dialog
    private ProgressDialog pDialog;
    private EditText operator_edit_Text;
    private Button new_operator_btn;
    private boolean doubleWildCard = false;
    private ArrayList<Operator> OperatorsArray = new ArrayList<>();
    private EditText username,password,password_again,first_name,last_name,phone;
    private ListView listView;
    private JSONArray JSONresponse;
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_operators);

        operator_edit_Text = (EditText) findViewById(R.id.operator_edit_Text);
        listView  = (ListView) findViewById(R.id.operators_listview);


        new_operator_btn = (Button) findViewById(R.id.new_operator_btn);
        new_operator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageOperators.this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = ManageOperators.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.edit_operator_dialog, null);
                dialogBuilder.setView(dialogView);
                //dialogBuilder.setTitle("Edit Operator");
                final AlertDialog alertDialog = dialogBuilder.create();

                final ArrayList<EditText> TextList = new ArrayList<>();
                username = (EditText) dialogView.findViewById(R.id.edit_operator_username);
                password = (EditText) dialogView.findViewById(R.id.edit_operator_password);
                password_again = (EditText) dialogView.findViewById(R.id.edit_operator_password_again);
                first_name = (EditText) dialogView.findViewById(R.id.edit_operator_first_name);
                last_name = (EditText) dialogView.findViewById(R.id.edit_operator_last_name);
                phone = (EditText) dialogView.findViewById(R.id.edit_operator_phone);
                TextList.add(username);
                TextList.add(password);
                TextList.add(password_again);
                TextList.add(first_name);
                TextList.add(last_name);
                TextList.add(phone);

                final Switch new_admin_switch = (Switch) dialogView.findViewById(R.id.new_administrator_switch);
                Button create_operator_btn = (Button) dialogView.findViewById(R.id.edit_operator_create_btn);
                Button cancel_operator_btn = (Button) dialogView.findViewById(R.id.edit_operator_cancel_btn);
                Button save_operator_btn = (Button) dialogView.findViewById(R.id.edit_operator_save_btn);
                Button delete_operator_btn = (Button) dialogView.findViewById(R.id.edit_operator_delete_btn);

                save_operator_btn.setVisibility(View.GONE);
                delete_operator_btn.setVisibility(View.GONE);
                create_operator_btn.setVisibility(View.VISIBLE);
                new_admin_switch.setVisibility(View.VISIBLE);

                cancel_operator_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                create_operator_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(EditText et:TextList){
                            if(et.getText().length()==0){
                                Toast.makeText(ManageOperators.this, R.string.enterValuesToAllFields,Toast.LENGTH_LONG).show();
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
                            new ManageOperators.AttemptCreateOperator().execute();
                            alertDialog.dismiss();
                        }else{
                            Toast.makeText(ManageOperators.this, R.string.checkYourPassword,Toast.LENGTH_LONG).show();
                            return;
                        }


                    }
                });

                alertDialog.show();

            }
        });

        operator_edit_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String sString = charSequence.toString();
                if(sString.length()!=0) {
                    params.clear();
                    params.add(new BasicNameValuePair("term", operator_edit_Text.getText().toString()));
                    if(doubleWildCard)
                        params.add(new BasicNameValuePair("double","true"));
                    new AttemptSearchOperator().execute();
                }else {
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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // EditCoupon = new Coupon(adapterList.get(position).getId(),adapterList.get(position).getName(),adapterList.get(position).getRequired_stamps());
                final Operator tempOp = OperatorsArray.get(position);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ManageOperators.this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = ManageOperators.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.edit_operator_dialog, null);
                dialogBuilder.setView(dialogView);
                //dialogBuilder.setTitle("Edit Operator");
                final AlertDialog alertDialog = dialogBuilder.create();

                EditText edit_operator_username = (EditText) dialogView.findViewById(R.id.edit_operator_username);
                EditText edit_operator_password = (EditText) dialogView.findViewById(R.id.edit_operator_password);
                EditText edit_operator_password_again = (EditText) dialogView.findViewById(R.id.edit_operator_password_again);
                EditText edit_operator_first_name = (EditText) dialogView.findViewById(R.id.edit_operator_first_name);
                EditText edit_operator_last_name = (EditText) dialogView.findViewById(R.id.edit_operator_last_name);
                EditText edit_operator_phone = (EditText) dialogView.findViewById(R.id.edit_operator_phone);

                try {
                    edit_operator_username.setText(JSONresponse.getJSONObject(position).getString("username"));
                    edit_operator_password.setText(JSONresponse.getJSONObject(position).getString("password"));
                    edit_operator_password_again.setText(JSONresponse.getJSONObject(position).getString("password"));
                    edit_operator_first_name.setText(JSONresponse.getJSONObject(position).getString("first_name"));
                    edit_operator_last_name.setText(JSONresponse.getJSONObject(position).getString("last_name"));
                    edit_operator_phone.setText(JSONresponse.getJSONObject(position).getString("phone"));
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

                final Switch new_admin_switch = (Switch) dialogView.findViewById(R.id.new_administrator_switch);
                Button create_operator_btn = (Button) dialogView.findViewById(R.id.edit_operator_create_btn);
                Button cancelButton = (Button) dialogView.findViewById(R.id.edit_operator_cancel_btn);
                Button deleteButton = (Button) dialogView.findViewById(R.id.edit_operator_delete_btn);
                Button saveButton = (Button) dialogView.findViewById(R.id.edit_operator_save_btn);

                saveButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                create_operator_btn.setVisibility(View.GONE);
                new_admin_switch.setVisibility(View.GONE);



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
                        if(tempOp.getId()<=0){
                            Toast.makeText(ManageOperators.this,"Error with operator ID.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        params.clear();
                        params.add(new BasicNameValuePair("id", String.valueOf(tempOp.getId()) ));
                        new AttemptDeleteOperator().execute();
                        alertDialog.dismiss();
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*if(edit_name.getText().equals(EditCoupon.getName()) &&  edit_stamps.getText().equals(EditCoupon.getRequired_stamps())){
                            Toast.makeText(ManageCouponsActivity.this,"No need to save anything.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        params.clear();
                        params.add(new BasicNameValuePair("id",String.valueOf(EditCoupon.getId())));
                        params.add(new BasicNameValuePair("name",String.valueOf(edit_name.getText().toString())));
                        params.add(new BasicNameValuePair("required",String.valueOf(edit_stamps.getText().toString())));
                        new ManageCouponsActivity.AttemptSaveCoupon().execute();
                        alertDialog.dismiss();*/
                    }
                });

                alertDialog.show();
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
            pDialog = new ProgressDialog(ManageOperators.this);
            pDialog.setMessage(getString(R.string.creatingOperator));
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

                JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_CREATION_URL), "GET", params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_CREATION_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Login!", json.toString());
                    return getString(R.string.operatorCreated);
                }else{
                    return getString(R.string.NameOrPhoneExists);

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
                Toast.makeText(ManageOperators.this,"Operator has been created.",Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(ManageOperators.this,OperatorCreation.class);
                i.putExtra("message",message);
                startActivity(i);*/
            }
        }
    }

    class AttemptSearchOperator extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(ManageCustomers.this);
            pDialog.setMessage("");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;

            Log.d("request!", "starting");

            JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.SEARCH_OPERATOR_URL), "GET", params);
            System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.SEARCH_OPERATOR_URL));
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
                OperatorsArray.clear();
                for (int i = 0; i < jsonResult.length(); i++) {
                    String label = jsonResult.getJSONObject(i).getString("first_name")+" "+jsonResult.getJSONObject(i).getString("last_name");
                    values[i] = label;
                    OperatorsArray.add(new Operator(jsonResult.getJSONObject(i).getInt("id"),
                                                    jsonResult.getJSONObject(i).getString("username"),
                                                    jsonResult.getJSONObject(i).getString("password"),
                                                    jsonResult.getJSONObject(i).getInt("access_level"),
                                                    jsonResult.getJSONObject(i).getString("first_name"),
                                                    jsonResult.getJSONObject(i).getString("last_name"),
                                                    jsonResult.getJSONObject(i).getString("phone")));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ManageOperators.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            /*
            pDialog.dismiss();
            if (message != null){
                Toast.makeText(ManageCustomers.this, message, Toast.LENGTH_LONG).show();
            }*/
        }
    }

    class AttemptDeleteOperator extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageOperators.this);
            pDialog.setMessage(getString(R.string.deletingOperator));
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

                JSONObject json = jsonParser.makeHttpRequest(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_DELETION_URL), "GET", params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_DELETION_URL));
                System.out.println(params);
                // checking  log for json response
                //Log.d("Login attempt", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1 && json.getString("message").equals("true")) {
                    Log.d("Successfully Deletion!", json.toString());
                    return getString(R.string.operatorDeleted);
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
                Toast.makeText(ManageOperators.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
