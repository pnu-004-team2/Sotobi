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

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;


public class pickup_matching extends AppCompatActivity {
    Intent intent;
    Timer mTimer;
    String Lat, Lng;
    String name,phonenumber,id;
    String tel;
    double rating;
    String rating_result;

    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAtgoztZ0:APA91bGR11-3PebRW6Pgnw0b7armEPD63nucupufYeUSnVd9Hxlyu9klN1xjjRrtY063oiPRjSYEEXyEUYH-v5OKW2dPMyQJtHyHescGqxQ6wWFu4qmLc8r6yXsdo_qPnEkcGmtgwfrK";

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
                        tel = "tel:" + phonenumber;
                        id = jsonResponse.getString("id");
                        rating = jsonResponse.getDouble("rating");
                        if(rating == -1){
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
                            TextView rating_View = (TextView)findViewById(R.id.pickup_rating);
                            rating_View.setText("평점기록이 없습니다.");
                        }
                        else{
                            rating_result = String.format("%.1f",rating);
                            rating_result += " / 5.0";

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
                            TextView rating_View = (TextView)findViewById(R.id.pickup_rating);
                            rating_View.setText(rating_result);
                        }
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    final String token = jsonResponse.getString("token");
                                    if(success){
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    // FMC 메시지 생성 start
                                                    JSONObject root = new JSONObject();
                                                    JSONObject notification = new JSONObject();
                                                    notification.put("content", "pick!");
                                                    notification.put("msg", "pick!");
                                                    notification.put("title", "승차 신청 완료! 내용을 확인하세요.");
                                                    root.put("notification", notification);
                                                    root.put("to", token);
                                                    // FMC 메시지 생성 end

                                                    URL Url = new URL(FCM_MESSAGE_URL);
                                                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection();
                                                    conn.setRequestMethod("POST");
                                                    conn.setDoOutput(true);
                                                    conn.setDoInput(true);
                                                    conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
                                                    conn.setRequestProperty("Accept", "application/json");
                                                    conn.setRequestProperty("Content-type", "application/json");
                                                    OutputStream os = conn.getOutputStream();
                                                    os.write(root.toString().getBytes("utf-8"));
                                                    os.flush();
                                                    conn.getResponseCode();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();

                                        //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        tokenGetRequest tokengetrequest = new tokenGetRequest(id,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(pickup_matching.this);
                        queue.add(tokengetrequest);

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
                                intent.putExtra("match_id", id);
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
