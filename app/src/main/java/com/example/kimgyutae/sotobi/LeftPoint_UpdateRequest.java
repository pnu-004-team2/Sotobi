package com.example.kimgyutae.sotobi;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LeftPoint_UpdateRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/Point_Update.php";
    private Map<String, String> params;

    public LeftPoint_UpdateRequest( String id, String point, com.android.volley.Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("point", point);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
