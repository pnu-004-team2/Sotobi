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

import org.json.JSONException;
import org.json.JSONObject;

public class pickme_complete_ready extends AppCompatActivity {
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_complete);

        String Dest  = intent.getStringExtra("Dest");
        String Point  = intent.getStringExtra("Point");
        EditText Where_go = (EditText)findViewById(R.id.pickme_Dest);
        EditText How_give_point = (EditText)findViewById(R.id.pickme_Give_point);

        Where_go.setText(Dest);
        How_give_point.setText(Point);

        // 승차 취소 버튼
        Button pickme_cancel_Btn = (Button)findViewById(R.id.pickme_cancel_Btn);
        pickme_cancel_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = getIntent();
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
                String Lat  = intent.getStringExtra("Lat");
                String Lng  = intent.getStringExtra("Lng");

                pickme_cancelRequest pickme_request = new pickme_cancelRequest(Lat,Lng, responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickme_complete_ready.this);
                queue.add(pickme_request);

                Intent intent = new Intent(pickme_complete_ready.this, pickme.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //수시로 정보를 받는 것을 구현해야한다.
    //Intent intent = new Intent(pickme_complete_ready.this, pickme_matching.class);
    //startActivity(intent);
    //finish();

}
