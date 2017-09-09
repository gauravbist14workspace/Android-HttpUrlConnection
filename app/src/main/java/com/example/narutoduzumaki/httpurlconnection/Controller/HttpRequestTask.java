package com.example.narutoduzumaki.httpurlconnection.Controller;

import android.content.Context;
import android.os.AsyncTask;

import com.example.narutoduzumaki.httpurlconnection.Models.HttpCall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Naruto D. Uzumaki on 2/25/2016.
 */
public class HttpRequestTask extends AsyncTask<HttpCall, String, String> {

    private static final String TAG = "HttpRequestTask";
    private Context context;

    public HttpRequestTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(HttpCall... params) {
        HttpURLConnection urlConnection = null;
        HttpCall httpCall = params[0];

        StringBuilder response = new StringBuilder();
        try {
            String dataParams = getDataParams(httpCall.getParams(), httpCall.getMethodType());
            URL url = new URL(httpCall.getMethodType() == HttpCall.GET ? httpCall.getUrl() + dataParams : httpCall.getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(httpCall.getMethodType() == HttpCall.GET ? "GET" : "POST");
//            urlConnection.setDoInput(true);     // true by default, it means we want to read data from url


            if (httpCall.getParams() != null && httpCall.getMethodType() == httpCall.POST) {
//                urlConnection.setDoOutput(true);    // false by default, we want to send some data to url
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.append(dataParams);
                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private String getDataParams(HashMap<String, String> params, int methodType) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isFirst) {
                isFirst = false;
                if (methodType == HttpCall.GET) {
                    result.append("?");
                }
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        onResponse(response);
    }

    public void onResponse(String response) {

    }
}
