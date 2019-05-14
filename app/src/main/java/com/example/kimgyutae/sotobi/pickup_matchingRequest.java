package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class pickup_matchingRequest extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/pickup_Matching.php";
    private Map<String, String> params;

    public pickup_matchingRequest(String id, String lat, String lng, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("Latitude", lat);
        params.put("Longitude", lng);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
