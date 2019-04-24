package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class pickup_matching extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_matching);



        // 만남 버튼
        Button pickup_meetBtn = (Button) findViewById(R.id.pickup_meet);
        pickup_meetBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 재확인하는 거 추가바람
                Intent intent = new Intent(pickup_matching.this, matching_done.class);
                startActivity(intent);
                finish();
            }
        });
        // 못 만남 버튼
        Button pickup_nonmeetBtn = (Button) findViewById(R.id.pickup_nonmeet);
        pickup_nonmeetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 재확인 하는 거 추가바람
                Intent intent = new Intent(pickup_matching.this, pickup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
