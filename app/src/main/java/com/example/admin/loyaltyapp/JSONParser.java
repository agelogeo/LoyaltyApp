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
    static JSONObject jsonObj ;
    static String json = "";

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


/*
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        // Make HTTP request
        try {

            // checking request method
            if(method == "POST"){

                // now defaultHttpClient object
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url +=  "&"+ paramString;
                System.out.println("URL: " +url);
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder str = new StringBuilder();
            String strLine = null;
            while ((strLine = reader.readLine()) != null) {
                str.append(strLine + "\n");
            }
            is.close();
            json = str.toString();
        } catch (Exception e) {

        }

        // now will try to parse the string into JSON object
        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {

        }


        return jsonObj;

    }*/

}