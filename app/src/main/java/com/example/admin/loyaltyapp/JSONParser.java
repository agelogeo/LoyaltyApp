package com.example.admin.loyaltyapp;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class JSONParser {
    static InputStream is = null;

    // default no argument constructor for jsonpaser class
    public JSONParser() {

    }


    public JSONObject getJSONFromUrl(final String url,List<NameValuePair> params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String paramString = URLEncodedUtils.format(params, "utf-8");
            URL urlink = new URL(url+"&"+ paramString);
            System.out.println("URLINK: " +urlink);
            connection = (HttpURLConnection) urlink.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(buffer.toString());
            } catch (JSONException e) {

            }
            return jsonObj;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}