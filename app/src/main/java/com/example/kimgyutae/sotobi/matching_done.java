package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.uPoint;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;


public class matching_done extends AppCompatActivity {
    int Point_Result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_done);

        Point_Result = Integer.parseInt(uPoint) - Integer.parseInt(Using_Point);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        TextView Point_View = (TextView)findViewById(R.id.matching_Left_Point);
                        Point_View.setText(Integer.toString(Point_Result));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LeftPoint_UpdateRequest inforequest = new LeftPoint_UpdateRequest(UserID, Integer.toString(Point_Result), responseListener);
        RequestQueue queue = Volley.newRequestQueue(matching_done.this);
        queue.add(inforequest);

        // 완료 버튼
        Button doneBtn = (Button)findViewById(R.id.matching_DoneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(matching_done.this, modeselect.class);
                startActivity(intent);
                finish();
            }
        });

    }
}