package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;

public class pickup_check extends AppCompatActivity {
    Intent intent;
    String Lat, Lng;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_check);
        intent = getIntent();

        Lat  = intent.getStringExtra("Lat");
        Lng  = intent.getStringExtra("Lng");
        String Dest  = intent.getStringExtra("Dest");
        final String Point  = intent.getStringExtra("Get_Point");

        EditText Where_go = (EditText)findViewById(R.id.pickup_Dest);
        EditText How_get_point = (EditText)findViewById(R.id.pickup_Get_Point);

        Where_go.setText(Dest);
        How_get_point.setText(Point);

        // 승락 버튼
        Button agreeBtn = (Button)findViewById(R.id.pickup_agreeBtn);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Using_Point = Integer.toString(Integer.parseInt(Point));
                                Intent intent = new Intent(pickup_check.this, pickup_matching.class);
                                intent.putExtra("Lat",Lat);
                                intent.putExtra("Lng",Lng);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                pickup_matchingRequest matchingrequest = new pickup_matchingRequest(UserID, Lat,Lng,responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickup_check.this);
                queue.add(matchingrequest);
            }
        });

        // 취소 버튼
        Button returntopickupBtn = (Button)findViewById(R.id.returntopickupBtn);
        returntopickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pickup_check.this, pickup.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(pickup_check.this, pickup.class);
        startActivity(intent);
        finish();
    }

}
