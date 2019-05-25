package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class history_register_request extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/History_SD.php";
    private Map<String, String> params;

    public history_register_request(String id, String time, String how, String mp, String point, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("time", time);
        params.put("how", how);
        params.put("movingpoint", mp);
        params.put("point", point);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
