package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class rentRegisterRequest extends StringRequest {
    private static final String VEHICLE_REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/RentRegister.php";
    private Map<String, String> params;

    public rentRegisterRequest(String id, long time, int point, int location, Response.Listener<String> listener) {
        super(Method.POST, VEHICLE_REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",id);
        params.put("time",String.valueOf(time));
        params.put("point",String.valueOf(point));
        params.put("location",String.valueOf(location));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
