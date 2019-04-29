package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class pickme_registerRequest extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/Matching.php";
    private Map<String, String> params;

    public pickme_registerRequest(String lat, String lng, String matched, String meet_me, String meet_up, String give_point, String dest, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Latitude", lat);
        params.put("Longitude", lng);
        params.put("Matched", matched);
        params.put("Meet_me", meet_me);
        params.put("Meet_up", meet_up);
        params.put("Give_Point", give_point);
        try {
            params.put("Dest", URLEncoder.encode(dest,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            params.put("Dest", dest);
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
