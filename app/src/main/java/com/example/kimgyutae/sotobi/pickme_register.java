package com.example.kimgyutae.sotobi;

/**
 * Created by kjh on 08/04/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;
import static com.example.kimgyutae.sotobi.modeselect.uPoint;
import static com.google.firebase.iid.FirebaseInstanceId.getInstance;

public class pickme_register extends AppCompatActivity{

    private static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAtgoztZ0:APA91bGR11-3PebRW6Pgnw0b7armEPD63nucupufYeUSnVd9Hxlyu9klN1xjjRrtY063oiPRjSYEEXyEUYH-v5OKW2dPMyQJtHyHescGqxQ6wWFu4qmLc8r6yXsdo_qPnEkcGmtgwfrK";

    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_register);
        intent = getIntent();
        final EditText left_point = (EditText)findViewById(R.id.pickme_Left_Point);
        left_point.setText(uPoint);

        // 승차 신청 버튼
        Button agreeBtn = (Button)findViewById(R.id.pickme_agreeBtn);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 응답 받기
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "승차 신청 완료!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "승차 신청 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditText How_give_point = (EditText)findViewById(R.id.pickme_Give_point);
                EditText Where_go = (EditText)findViewById(R.id.pickme_Dest);
                Using_Point = How_give_point.getText().toString();
                String Dest = Where_go.getText().toString();
                String Lat  = intent.getStringExtra("Lat");
                String Lng  = intent.getStringExtra("Lng");

                if(Dest.length() > 0 && Using_Point.length() > 0){
                    //서버 전송
                    pickme_registerRequest pickme_request = new pickme_registerRequest(Lat,Lng,"x", UserID,"x", Using_Point, Dest, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(pickme_register.this);
                    queue.add(pickme_request);

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
                                    RequestQueue queue = Volley.newRequestQueue(pickme_register.this);
                                    queue.add(tokenregisterrequest);
                                }
                            });

                    Intent intent = new Intent(pickme_register.this, pickme_complete_ready.class);
                    intent.putExtra("Lat",Lat);
                    intent.putExtra("Lng",Lng);
                    intent.putExtra("Dest",Dest);
                    intent.putExtra("Left_Point",uPoint);

                    modeselect.Psave(uPoint, Using_Point);

                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력바랍니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 승차 취소 버튼
        Button returntopickmeBtn = (Button)findViewById(R.id.returntopickmeBtn);
        returntopickmeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pickme_register.this, pickme.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(pickme_register.this, pickme.class);
        startActivity(intent);
        finish();
    }

}


