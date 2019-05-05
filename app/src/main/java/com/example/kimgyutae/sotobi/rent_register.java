package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class rent_register extends AppCompatActivity {
    private Spinner spinner_hour;
    ArrayList<Integer> timeList;
    ArrayAdapter<Integer> timeAdapter;

    private Spinner spinner_min;
    ArrayList<Integer> timeList_min;
    ArrayAdapter<Integer> timeAdapter_min;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcanrent);

        timeList = new ArrayList<>();
        timeList.add(0);
        timeList.add(1);
        timeList.add(2);
        timeList.add(3);
        timeList.add(4);
        timeList.add(5);
        timeList.add(6);
        timeList.add(7);
        timeList.add(8);
        timeList.add(9);
        timeList.add(10);
        timeList.add(11);
        timeList.add(12);
        timeList.add(13);
        timeList.add(14);
        timeList.add(15);
        timeList.add(16);
        timeList.add(17);
        timeList.add(18);
        timeList.add(19);
        timeList.add(20);
        timeList.add(21);
        timeList.add(22);
        timeList.add(23);

        timeAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                timeList);

        spinner_hour = (Spinner)findViewById(R.id.rent_hour);
        spinner_hour.setAdapter(timeAdapter);

        timeList_min = new ArrayList<>();
        timeList_min.add(0);
        timeList_min.add(10);
        timeList_min.add(20);
        timeList_min.add(30);
        timeList_min.add(40);
        timeList_min.add(50);

        timeAdapter_min = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                timeList_min);

        spinner_min = (Spinner)findViewById(R.id.rent_min);
        spinner_min.setAdapter(timeAdapter_min);

        // 대여 시간 설정
        final EditText left_point = (EditText)findViewById(R.id.Left_Point);
        final EditText rent_point = (EditText)findViewById(R.id.Rent_Point);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String uPoint = jsonResponse.getString("point");
                    left_point.setText(uPoint);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        pointRequest pointrequest = new pointRequest(UserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(rent_register.this);
        queue.add(pointrequest);


        spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rent = 0;
                rent = (spinner_hour.getSelectedItemPosition()*6) + spinner_min.getSelectedItemPosition();
                rent_point.setText(String.valueOf(rent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rent = 0;
                rent = (spinner_hour.getSelectedItemPosition()*6) + spinner_min.getSelectedItemPosition();
                rent_point.setText(String.valueOf(rent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 대여 신청 버튼
        Button agreerentBtn = (Button)findViewById(R.id.agreerentBtn);
        agreerentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Intent intent = new Intent(rent_register.this, rent_complete.class);
                            intent.putExtra("point_time", (spinner_hour.getSelectedItemPosition()*6) + spinner_min.getSelectedItemPosition());
                            // 앞에서 받은 latlng을 이용해서 그 오토바이에 하나 사용
                            startActivity(intent);
                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                long regTime = System.currentTimeMillis();
                regTime += (spinner_hour.getSelectedItemPosition()*6 + spinner_min.getSelectedItemPosition())*60*1000;


                rentRegisterRequest RRrequest = new rentRegisterRequest(UserID, regTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rent_register.this);
                queue.add(RRrequest);

            }
        });

        // 대여 취소 버튼
        Button returntorentBtn = (Button)findViewById(R.id.returntorentBtn);
        returntorentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rent_register.this, rent.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(rent_register.this, rent.class);
        startActivity(intent);
        finish();
    }
}
