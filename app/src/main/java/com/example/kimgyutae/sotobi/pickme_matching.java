package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class pickme_matching extends AppCompatActivity {
    Intent intent;
    Timer mTimer;
    String Lat, Lng;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_matching);
        intent = getIntent();

        Lat  = intent.getStringExtra("Lat");
        Lng  = intent.getStringExtra("Lng");

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(!success){
                                Toast.makeText(getApplicationContext(), "상대가 취소하였습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(pickme_matching.this, pickme.class);
                                startActivity(intent);
                                mTimer.cancel();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                IsCancelRequest iscancelrequest = new IsCancelRequest(Lat,Lng,responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickme_matching.this);
                queue.add(iscancelrequest);

                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(pickme_matching.this, matching_done.class);
                                startActivity(intent);
                                mTimer.cancel();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                IsMatchingRequest ismatchingrequest = new IsMatchingRequest(Lat,Lng,responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(pickme_matching.this);
                queue2.add(ismatchingrequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,5000);

        // 만남 버튼
        Button pickme_meetBtn = (Button) findViewById(R.id.pickme_meet);
        pickme_meetBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "만났군요! 잠시만 기다려주세요!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                pickme_MeetRequest pickme_meetrequest = new pickme_MeetRequest(Lat,Lng,responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickme_matching.this);
                queue.add(pickme_meetrequest);

            }
        });
        // 못 만남 버튼
        Button pickme_nonmeetBtn = (Button) findViewById(R.id.pickme_nonmeet);
        pickme_nonmeetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "승차 취소 완료!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "승차 취소 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                pickme_cancelRequest pickme_request = new pickme_cancelRequest(Lat,Lng, responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickme_matching.this);
                queue.add(pickme_request);

                Intent intent = new Intent(pickme_matching.this, pickme.class);
                startActivity(intent);
                mTimer.cancel();
                finish();
            }
        });
    }
}
