package com.example.kimgyutae.sotobi;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rent_completeRequest extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/Rentcheck.php";
    private Map<String, String> params;

    public rent_completeRequest(String id, com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params.put("id",id);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}