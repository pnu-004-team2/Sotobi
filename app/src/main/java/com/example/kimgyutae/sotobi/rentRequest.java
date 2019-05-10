package com.example.kimgyutae.sotobi;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rentRequest extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/Map_rent.php";
    private Map<String, String> params;

    public rentRequest(com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
