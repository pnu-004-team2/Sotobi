package com.example.kimgyutae.sotobi;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

/**
 *  Created by hdy 19-04-21
 */
public class vehicle_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleregister);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        /* 등록 버튼 */
        Button regiBtn = (Button)findViewById(R.id.vehicle_register);
        regiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_model = (EditText) findViewById(R.id.vehicle_model);
                EditText et_number = (EditText) findViewById(R.id.vehicle_number);
                String model = et_model.getText().toString();
                String number = et_number.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "오토바이 등록 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(vehicle_register.this, myinfo.class);
                                vehicle_register.this.startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (model.length() > 0) {
                    if (number.length() > 0) {// 서버 전송
                        //Toast.makeText(getApplicationContext(), "전송", Toast.LENGTH_SHORT).show();
                        vehicle_registerRequest Vrequest = new vehicle_registerRequest(id,model,number, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(vehicle_register.this);
                        queue.add(Vrequest);
                        } else  Toast.makeText(getApplicationContext(), "차량 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    } else  Toast.makeText(getApplicationContext(), "차량 종류를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        /* 취소 버튼 */
        Button cancelBtn = (Button)findViewById(R.id.vehicle_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vehicle_register.this, myinfo.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

