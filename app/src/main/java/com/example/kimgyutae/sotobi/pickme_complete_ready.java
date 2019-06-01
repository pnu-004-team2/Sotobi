package com.example.kimgyutae.sotobi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.kimgyutae.sotobi.modeselect.Using_Point;

public class pickme_complete_ready extends AppCompatActivity {
    Intent intent;
    String Lat, Lng;
    Timer mTimer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_complete);
        intent = getIntent();

        Lat  = intent.getStringExtra("Lat");
        Lng  = intent.getStringExtra("Lng");
        modeselect.Lsave(Lat,Lng);
        String Dest  = intent.getStringExtra("Dest");
        String Left_Point  = intent.getStringExtra("Left_Point");

        EditText Where_go = (EditText)findViewById(R.id.pickme_Dest);
        EditText How_give_point = (EditText)findViewById(R.id.pickme_Give_point);
        EditText Left_point = (EditText)findViewById(R.id.pickme_Left_Point);

        Where_go.setText(Dest);
        How_give_point.setText(Using_Point);
        Left_point.setText(Left_Point);

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(pickme_complete_ready.this, pickme_matching.class);
                                startActivity(intent);
                                mTimer.cancel();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                pickme_matchingRequest matchingrequest = new pickme_matchingRequest(Lat,Lng,responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickme_complete_ready.this);
                queue.add(matchingrequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,3000);

        // 승차 취소 버튼
        Button pickme_cancel_Btn = (Button)findViewById(R.id.pickme_cancel_Btn);
        pickme_cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(pickme_complete_ready.this);
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
                                RequestQueue queue = Volley.newRequestQueue(pickme_complete_ready.this);
                                queue.add(pickme_request);

                                modeselect.Lsave("","");
                                Intent intent = new Intent(pickme_complete_ready.this, pickme.class);
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
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(pickme_complete_ready.this);
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
                        RequestQueue queue = Volley.newRequestQueue(pickme_complete_ready.this);
                        queue.add(pickme_request);

                        modeselect.Lsave("","");
                        Intent intent = new Intent(pickme_complete_ready.this, pickme.class);
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
