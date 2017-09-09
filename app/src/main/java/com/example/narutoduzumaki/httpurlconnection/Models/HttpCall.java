package com.example.narutoduzumaki.httpurlconnection.Models;

import java.util.HashMap;

/**
 * Created by Naruto D. Uzumaki on 06-09-2017.
 */

public class HttpCall {
    public static final int GET = 1;
    public static final int POST = 2;

    private String url;
    private int methodType;
    private HashMap<String, String> params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
