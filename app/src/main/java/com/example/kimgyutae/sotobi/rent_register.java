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

import java.util.ArrayList;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;
import static com.example.kimgyutae.sotobi.modeselect.uPoint;
import static com.example.kimgyutae.sotobi.rent_complete.bnum;

public class rent_register extends AppCompatActivity {
    private Spinner spinner_hour;
    ArrayList<Integer> timeList;
    ArrayAdapter<Integer> timeAdapter;

    private Spinner spinner_min;
    ArrayList<Integer> timeList_min;
    ArrayAdapter<Integer> timeAdapter_min;
    int usePoints;
    public static int orgPoints;
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

        left_point.setText(uPoint);
        orgPoints = Integer.parseInt(uPoint);

        final calculate_point cp = new calculate_point(orgPoints);

        spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int rent = 0;
                rent = (spinner_hour.getSelectedItemPosition()*6) + spinner_min.getSelectedItemPosition();
                rent_point.setText(String.valueOf(rent));
                usePoints = cp.rent_calculate_point(rent);
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
                usePoints = orgPoints - rent;
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
                Intent location = getIntent();
                double Lat = Double.parseDouble(location.getStringExtra("Lat"));
                double Lng = Double.parseDouble(location.getStringExtra("Lng"));

                int locat = 0;
                int check = 0;

                if(Lat == 35.231457 && Lng == 129.0839022)
                    locat = 1;
                else if(Lat == 35.23805 && Lng == 129.077125)
                    locat = 2;
                else if(Lat == 35.2358219 && Lng == 129.0828055)
                    locat = 3;
                else if(Lat == 35.2338779 && Lng == 129.0753506)
                    locat = 4;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                bnum = jsonResponse.getString("bnum");
                                Intent intent = new Intent(rent_register.this, rent_complete.class);
                                intent.putExtra("point_time", (spinner_hour.getSelectedItemPosition() * 6) + spinner_min.getSelectedItemPosition());
                                // 앞에서 받은 latlng을 이용해서 그 오토바이에 하나 사용
                                Using_Point = String.valueOf(orgPoints-usePoints);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");

                                            Intent intent = new Intent(rent_register.this, rent.class);
                                            startActivity(intent);
                                            finish();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                rentedRequest rentedrequest = new rentedRequest(UserID, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(rent_register.this);
                                queue.add(rentedrequest);
                                Toast.makeText(getApplicationContext(), "현재 장소에 대여가능한 오토바이가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };

                long regTime = System.currentTimeMillis();
                regTime += (spinner_hour.getSelectedItemPosition()*60 + spinner_min.getSelectedItemPosition()*10)*60*1000;


                rentRegisterRequest RRrequest = new rentRegisterRequest(UserID, regTime, usePoints , locat, responseListener);
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
