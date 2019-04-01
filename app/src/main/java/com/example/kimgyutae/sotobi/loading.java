package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class loading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // 로딩화면 2.5초 대기
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(loading.this, login.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
