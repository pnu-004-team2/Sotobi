package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kjh on 08/04/2019.
 */

public class registerRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/Register.php";
    private static final String REGISTER_REQUEST_URL1 = "http://jwu8615.dothome.co.kr/Modified.php";
    private Map<String, String> params;

    public registerRequest(String email, String id, String name, String password, String phonenumber, com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("id", id);
        try {
            params.put("name", URLEncoder.encode(name,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            params.put("name", name);
        }
        params.put("password", password);
        params.put("phonenumber", phonenumber);
        params.put("motormodel", "x");
        params.put("motornumber", "x");
        params.put("motorcompany", "x");
        params.put("point", "100");
    }
    public registerRequest( String id, com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL1, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
