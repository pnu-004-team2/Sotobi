package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class rent_complete extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_complete);
        //시간 계산 필요

        // 반납 신청 버튼
        Button returnBtn = (Button)findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rent_complete.this, return_rent.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
