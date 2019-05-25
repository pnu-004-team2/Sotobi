package com.example.kimgyutae.sotobi;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rentingRequest extends StringRequest {

    private static final String VEHICLE_REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/RentingRegister.php";
    private Map<String, String> params;

    public rentingRequest(String bnum, Response.Listener<String> listener) {
        super(Request.Method.POST, VEHICLE_REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("bnum",bnum);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
