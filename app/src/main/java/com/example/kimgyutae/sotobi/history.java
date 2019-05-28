package com.example.kimgyutae.sotobi;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class history extends AppCompatActivity {
    private ListView list;
    ListViewAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        adapter = new ListViewAdapter() ;
        list = (ListView) findViewById(R.id.list);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("Show");

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar;
                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject item = jsonArray.getJSONObject(i);

                        String Time = item.getString("Time");
                        String How = item.getString("How");
                        String MP = item.getString("MovingPoint");
                        String Point = item.getString("Point");


                        // Create a calendar object that will convert the date and time value in milliseconds to date.
                        calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(Long.parseLong(Time));
                        String date = formatter.format(calendar.getTime());
                        date = date.substring(0,19);

                        if((MP.charAt(0)!='-')){
                            MP = '+' + MP;
                        }

                        Log.e("adapter", date + " " + How + " " + MP + " " + Point);
                        adapter.addItem(date,How,MP,Point);
                    }


                    list.setAdapter(adapter);


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
