package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kjh on 08/04/2019.
 */

public class passChangeRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/updatePW.php";
    private Map<String, String> params;

    public passChangeRequest( String id, String password,com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
