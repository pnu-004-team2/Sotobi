package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
                                intent.putExtra("Lat",Lat);
                                intent.putExtra("Lng",Lng);
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
        mTimer.schedule(mTask,0,5000);

        // 승차 취소 버튼
        Button pickme_cancel_Btn = (Button)findViewById(R.id.pickme_cancel_Btn);
        pickme_cancel_Btn.setOnClickListener(new View.OnClickListener() {
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
                RequestQueue queue = Volley.newRequestQueue(pickme_complete_ready.this);
                queue.add(pickme_request);

                Intent intent = new Intent(pickme_complete_ready.this, pickme.class);
                startActivity(intent);
                mTimer.cancel();
                finish();
            }
        });
    }

}
