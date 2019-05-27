package com.example.kimgyutae.sotobi;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import android.content.Intent;




public class rating_request extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/rating.php";
    private Map<String, String> params;



    public rating_request(String match_id, String rating,com.android.volley.Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", match_id);
        params.put("rating", rating);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
