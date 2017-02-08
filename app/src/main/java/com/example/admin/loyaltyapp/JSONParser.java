package com.example.admin.loyaltyapp;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;


class JSONParser {

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

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
                //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return new JSONObject(buffer.toString());

        } catch (JSONException | IOException e) {
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