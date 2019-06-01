package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.appData;

public class loading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        appData = getSharedPreferences("appData", MODE_PRIVATE);

        // 로딩화면 2.5초 대기
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                modeselect.load();
                if(UserID != null){
                    Intent intent = new Intent(loading.this, modeselect.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(loading.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500);
    }
}
