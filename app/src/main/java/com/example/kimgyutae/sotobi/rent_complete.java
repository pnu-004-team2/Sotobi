package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class rent_complete extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_complete);
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

        rent_completeRequest rent_completeRequest = new rent_completeRequest(UserID,responseListener);
        RequestQueue queue = Volley.newRequestQueue(rent_complete.this);
        queue.add(rent_completeRequest);




        // 반납 신청 버튼
        Button returnBtn = (Button)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Intent intent = new Intent(rent_complete.this, return_rent.class);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rentedRequest rentedrequest = new rentedRequest(UserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(rent_complete.this);
                queue.add(rentedrequest);

            }
        });
    }
}
