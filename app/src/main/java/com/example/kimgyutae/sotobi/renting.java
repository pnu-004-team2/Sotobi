package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;
import static com.example.kimgyutae.sotobi.rent_register.orgPoints;

public class renting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_wait);
        Button returnBtn = (Button)findViewById(R.id.rent_returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Intent intent = new Intent(renting.this, return_rent.class);
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rentedRequest rentedrequest = new rentedRequest(UserID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(renting.this);
                queue.add(rentedrequest);
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        int point = orgPoints- Integer.parseInt(Using_Point);
        history_register_request historyregisterrequest = new history_register_request(UserID,
                Long.toString(System.currentTimeMillis()),"rent",Using_Point,String.valueOf(point),responseListener);
        RequestQueue queue = Volley.newRequestQueue(renting.this);
        queue.add(historyregisterrequest);
    }
}
