package com.example.kimgyutae.sotobi;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class history extends AppCompatActivity {
    ArrayList<String> Time_List = new ArrayList();
    ArrayList<String> How_List = new ArrayList();
    ArrayList<String> MP_List = new ArrayList();
    ArrayList<String> Point_List = new ArrayList();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("Show");
                    Time_List.clear();
                    How_List.clear();
                    MP_List.clear();
                    Point_List.clear();

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject item = jsonArray.getJSONObject(i);

                        String Time = item.getString("Time");
                        String How = item.getString("How");
                        String MP = item.getString("MovingPoint");
                        String Point = item.getString("Point");

                        Time_List.add(Time);
                        How_List.add(How);
                        MP_List.add(MP);
                        Point_List.add(Point);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                }
            }
        };
        history_view_request historyrequest = new history_view_request(UserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(history.this);
        queue.add(historyrequest);


    }
}
