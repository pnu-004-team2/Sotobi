package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class pointRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://jwu8615.dothome.co.kr/Pointcheck.php";
    private Map<String, String> params;

    public pointRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
