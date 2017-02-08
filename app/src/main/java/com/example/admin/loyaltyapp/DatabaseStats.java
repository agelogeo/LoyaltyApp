package com.example.admin.loyaltyapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_stats);

        params.clear();
        params.add(new BasicNameValuePair("filter","default"));
        db_list = (ListView) findViewById(R.id.db_list_view);
        waitText = (TextView) findViewById(R.id.db_waitText);
        new AttemptGetDB().execute();

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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DatabaseStats.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
                db_list.setAdapter(adapter);

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
}
