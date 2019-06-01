
package com.example.kimgyutae.sotobi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class modeselect extends AppCompatActivity {
    public static String UserID;
    public static String Using_Point;
    public static String uPoint;

    public static SharedPreferences appData;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeselect);

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        load();

        // 에러
        ImageView errorreport = (ImageView)findViewById(R.id.error_report);
        errorreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, errorreport.class);
                startActivity(intent);
            }
        });

        // 내 정보
        ImageView myinfo = (ImageView)findViewById(R.id.my_info);
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, myinfo.class);
                startActivity(intent);
            }
        });

        // 대여
        ImageButton rent = (ImageButton)findViewById(R.id.gotorent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                Intent intent = new Intent(modeselect.this, rent.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(modeselect.this, renting.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rent_completeRequest rent_completerequest = new rent_completeRequest(UserID, "PW", responseListener);
                RequestQueue queue = Volley.newRequestQueue(modeselect.this);
                queue.add(rent_completerequest);
            }
        });

        // 승차 요청
        ImageButton pickme = (ImageButton)findViewById(R.id.gotopickme);
        pickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickme.class);
                startActivity(intent);
            }
        });
        // 동승 찾기
        ImageButton pickup = (ImageButton)findViewById(R.id.gotopickup);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickup.class);
                startActivity(intent);
            }
        });


        String Lat  = appData.getString("Lat", "");
        String Lng  = appData.getString("Lng", "");

        if(Lat!=""&&Lng!=""){
            Intent intent = new Intent(modeselect.this, pickme_matching.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(modeselect.this);
        alert_confirm.setMessage("로그아웃 하시겠습니다?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES'
                        UserID = null;
                        save();
                        Intent intent = new Intent(modeselect.this, login.class);
                        startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    uPoint = jsonResponse.getString("point");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        pointRequest pointrequest = new pointRequest(UserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(modeselect.this);
        queue.add(pointrequest);
    }


    // 설정값을 저장하는 함수
    public static void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putString("ID", UserID);

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    public static void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        String s = appData.getString("ID", "");
        if(s.length()>4){
            UserID = s;
        }
    }

    // 설정값을 저장하는 함수
    public static void Lsave(String Lat, String Lng) {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putString("Lat", Lat);
        editor.putString("Lng", Lng);

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 저장하는 함수
    public static void Psave(String uP, String usP) {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putString("uP", uP);
        editor.putString("usP", usP);

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }
}

