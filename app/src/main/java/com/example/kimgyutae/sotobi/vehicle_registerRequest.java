package com.example.kimgyutae.sotobi;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  created by hdy 19/04/21
 */

public class vehicle_registerRequest extends StringRequest {
    private static final String VEHICLE_REGISTER_REQUEST_URL = "http://jwu8615.dothome.co.kr/vehicleRegister.php";
    private Map<String, String> params;

    public vehicle_registerRequest(String id, String motormodel, String motornumber,  Response.Listener<String> listener) {
        super(Method.POST, VEHICLE_REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",id);
        params.put("motormodel", motormodel);
        params.put("motornumber", motornumber);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
