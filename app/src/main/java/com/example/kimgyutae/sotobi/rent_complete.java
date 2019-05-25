package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class rent_complete extends AppCompatActivity {
    String password;
    Timer mTimer;

    Intent BikeN = getIntent();
    public static String bnum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_complete);

        TextView rent_PW = (TextView)findViewById(R.id.rentPW);
        TextView bikenum = (TextView)findViewById(R.id.motor_number);

        password = createCode.getCode().substring(0,4);
        rent_PW.setText(password);
        bikenum.setText(bnum);

        // modified by hdy
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                        String timestamp = jsonResponse.getString("time"); //받아와서

                        long time = Long.parseLong(timestamp); //바꾸고
                        long currtime = System.currentTimeMillis();

                        long resulttime = time - currtime;

                        String stringtime = Long.toString(resulttime);
                        String stringtimeH = Long.toString(resulttime/1000/60/60);
                        String stringtimeM = Long.toString(resulttime/1000/60%60);

                        TextView left_time = (TextView)findViewById(R.id.Left_time);
                        left_time.setText(stringtimeH +"시 : "+ stringtimeM+"분");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        rent_completeRequest rent_completeRequest = new rent_completeRequest(UserID,password,responseListener);
        RequestQueue queue = Volley.newRequestQueue(rent_complete.this);
        queue.add(rent_completeRequest);

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String state = jsonResponse.getString("state");
                            if (state.equals("2")) {
                                Toast.makeText(getApplicationContext(), "대여를 시작합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(rent_complete.this, renting.class);
                                startActivity(intent);
                                mTimer.cancel();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rentingRequest Rrequest = new rentingRequest(bnum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rent_complete.this);
                queue.add(Rrequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,5000);

        // 반납 신청 버튼
        Button returntorentBtn = (Button)findViewById(R.id.returnBtn);
        returntorentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }
}
