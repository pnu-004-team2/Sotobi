package com.example.kimgyutae.sotobi;

/**
 * Created by kjh on 08/04/2019.
 */

import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DupcheckRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/Dupcheck.php";
    private Map<String, String> params;

    public DupcheckRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}