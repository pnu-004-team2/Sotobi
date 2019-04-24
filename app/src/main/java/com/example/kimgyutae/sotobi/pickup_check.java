package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class pickup_check extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_check);

        // 승락 버튼
        Button agreeBtn = (Button)findViewById(R.id.pickup_agreeBtn);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pickup_check.this, pickme_complete_ready.class);
                startActivity(intent);
                finish();
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
