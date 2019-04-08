package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kjh on 08/04/2019.
 */

public class loginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://jwu8615.dothome.co.kr/Login.php";
    private Map<String, String> params;

    public loginRequest(String id, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}