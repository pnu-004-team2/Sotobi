package com.example.kimgyutae.sotobi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;


public class pickup_matching extends AppCompatActivity {
    Intent intent;
    Timer mTimer;
    String Lat, Lng;
    String name,phonenumber;
    String tel;
    String match_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_matching);
        intent = getIntent();

        Lat  = intent.getStringExtra("Lat");
        Lng  = intent.getStringExtra("Lng");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        name = URLDecoder.decode(jsonResponse.getString("name"),"utf-8");
                        phonenumber = jsonResponse.getString("phonenumber");
                        match_id = jsonResponse.getString("id");
                        tel = "tel:" + phonenumber;

                        TextView name_View = (TextView)findViewById(R.id.pickup_name);
                        name_View.setText(name);
                        TextView phonenumber_View = (TextView)findViewById(R.id.pickup_phone);
                        phonenumber_View.setText(phonenumber);
                        phonenumber_View.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        pickup_GetinfoRequest inforequest = new pickup_GetinfoRequest(Lat,Lng,responseListener);
        RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
        queue.add(inforequest);

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
                                Intent intent = new Intent(pickup_matching.this, pickup.class);
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
                RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
                queue.add(iscancelrequest);

                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(pickup_matching.this, matching_done.class);
                                intent.putExtra("match_id", match_id);
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
                RequestQueue queue2 = Volley.newRequestQueue(pickup_matching.this);
                queue2.add(ismatchingrequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,3000);

        // 만남 버튼
        Button pickup_meetBtn = (Button) findViewById(R.id.pickup_meet);
        pickup_meetBtn .setOnClickListener(new View.OnClickListener() {
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
                pickup_MeetRequest pickup_meetrequest = new pickup_MeetRequest(Lat,Lng,responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
                queue.add(pickup_meetrequest);
            }
        });
        // 못 만남 버튼
        Button pickup_nonmeetBtn = (Button) findViewById(R.id.pickup_nonmeet);
        pickup_nonmeetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(pickup_matching.this);
                alert_confirm.setMessage("승차 취소를 하시겠습니다?").setCancelable(false).setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'YES'
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
                                RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
                                queue.add(pickme_request);

                                Intent intent = new Intent(pickup_matching.this, pickup.class);
                                startActivity(intent);
                                mTimer.cancel();
                                finish();
                            }
                        }).setNegativeButton("아니요",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 'No'
                                return;
                            }
                        });
                AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(pickup_matching.this);
        alert_confirm.setMessage("승차 취소를 하시겠습니다?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES'
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
                        RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
                        queue.add(pickme_request);

                        Intent intent = new Intent(pickup_matching.this, pickup.class);
                        startActivity(intent);
                        mTimer.cancel();
                        finish();
                    }
                }).setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }
}
