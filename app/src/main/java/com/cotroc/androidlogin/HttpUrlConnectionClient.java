package com.cotroc.androidlogin;

/**
 * Created by Cotroc on 22/08/2017.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUrlConnectionClient {
    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final String TAG = "HttpUrlConnectionClient";

    public String sendGet(String urlServer) {
        URL url;
        String list = null;
        try {
            url = new URL(urlServer);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(GET);
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                list = inputLine.toString();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    //httpUrlConnectionClient.sendPost("http://192.168.1.46:8080/persona/",jsonObject.toString());
    public String sendPost(String urlServer, String args) {
        StringBuffer result = new StringBuffer();
        String inputLine;
        try {
            byte[] bytes = args.getBytes("UTF-8");
            URL url = new URL(urlServer);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json; charset=UTF-8");
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(bytes);
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while (null!=(inputLine = in.readLine())) {
                result.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            //add message error file not fuound, no server.
        }
        return result.toString();
    }
}