package com.example.kimgyutae.sotobi;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class history_view_request extends StringRequest {
    private static final String REQUEST_URL = "http://jwu8615.dothome.co.kr/History_LD.php";
    private Map<String, String> params;

    public history_view_request(String id, Response.Listener<String> listener) {
        super(Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
