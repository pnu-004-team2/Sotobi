package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class rentedRequest extends StringRequest {
    private static final String VEHICLE_REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/RentED.php";
    private Map<String, String> params;

    public rentedRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, VEHICLE_REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
