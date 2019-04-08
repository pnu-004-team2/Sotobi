package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class rent_register extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcanrent);

        Button agreerentBtn = (Button)findViewById(R.id.agreerentBtn);
        agreerentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 대여를 시작한다.
                // 구현 필요
            }
        });
        Button returntorentBtn = (Button)findViewById(R.id.returntorentBtn);
        returntorentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rent_register.this, rent.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(rent_register.this, rent.class);
        startActivity(intent);
        finish();
    }
}
