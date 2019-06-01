
package com.example.kimgyutae.sotobi;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

import javax.mail.Message;

import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class modeselect extends AppCompatActivity {
    public static String UserID;
    public static String Using_Point;
    public static String uPoint;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeselect);


        FireBaseMessagingService fbms = new FireBaseMessagingService();
        fbms.onNewToken(UserID);
        getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (!success) {
                                    } else {
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        //Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

                        tokenRegisterRequest tokenregisterrequest = new tokenRegisterRequest(UserID, token, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(modeselect.this);
                        queue.add(tokenregisterrequest);
                    }
                });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String token = jsonResponse.getString("token");
                    if(success){
                        /*
                        // This registration token comes from the client FCM SDKs.
                        String registrationToken = "YOUR_REGISTRATION_TOKEN";

                        RemoteMessage message;
                        message = RemoteMessage.Builder.Build;


                        String FBresponse = FirebaseMessaging.getInstance().send(message);
                        */
                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tokenGetRequest tokengetrequest = new tokenGetRequest(UserID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(modeselect.this);
        queue.add(tokengetrequest);

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
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(modeselect.this);
        alert_confirm.setMessage("로그아웃 하시겠습니다?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES'
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
}

