package com.example.narutoduzumaki.httpurlconnection.Views.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.narutoduzumaki.httpurlconnection.Controller.HttpRequestTask;
import com.example.narutoduzumaki.httpurlconnection.Models.HttpCall;
import com.example.narutoduzumaki.httpurlconnection.R;
import com.example.narutoduzumaki.httpurlconnection.Views.Fragments.CustomDialogFragment;

import java.util.HashMap;

/**
 * Created by Naruto D. Uzumaki on 2/25/2016.
 */
public class MainActivity extends AppCompatActivity implements CustomDialogFragment.OnDialogSubmitListener {
    private static final String TAG = "MainActivity";

    // widgets
    ProgressBar progressBar;
    TextView textView;
    private ProgressDialog mProgressDialog;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        init();
    }

    private void bindViews() {
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    private void init() {
        getIPAddressFromUser();
    }

    private void getIPAddressFromUser() {
        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "DialogFragment");
    }

    @Override
    public void onSubmitClicked(String stringUrl) {
        url = stringUrl;

        progressBar.setIndeterminate(true);

        // GET params setup
        HttpCall httpCall = new HttpCall();

        HashMap<String, String> params = new HashMap<>();
        params.put("name", "James Bond");
        httpCall.setParams(params);
        httpCall.setMethodType(HttpCall.GET);
        httpCall.setUrl(url);

        // executing the GET request
        new HttpRequestTask(this) {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                textView.setText("Get: " + response);

                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
            }
        }.execute(httpCall);

        // POST params setup
        HttpCall httpCallPost = new HttpCall();

        HashMap<String, String> paramsPost = new HashMap<>();
        paramsPost.put("name", "Julius Caesar");
        httpCallPost.setParams(paramsPost);
        httpCallPost.setMethodType(HttpCall.POST);
        httpCallPost.setUrl(url);

        // executing the POST response
        new HttpRequestTask(this) {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                textView.setText(textView.getText() + "\nPost: " + response);

                if (progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
            }
        }.execute(httpCallPost);
    }

    @Override
    public void onCancelClicked() {
        Log.d(TAG, "onCancelClicked: You need to provide the uri to continue.");
        getIPAddressFromUser();
    }
}
