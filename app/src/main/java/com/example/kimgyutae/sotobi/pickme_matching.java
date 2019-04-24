package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class pickme_matching extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_matching);



        // 만남 버튼
        Button pickme_meetBtn = (Button) findViewById(R.id.pickme_meet);
        pickme_meetBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 재확인하는 거 추가바람
                Intent intent = new Intent(pickme_matching.this, matching_done.class);
                startActivity(intent);
                finish();
            }
        });
        // 못 만남 버튼
        Button pickme_nonmeetBtn = (Button) findViewById(R.id.pickme_nonmeet);
        pickme_nonmeetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 재확인 하는 거 추가바람
                Intent intent = new Intent(pickme_matching.this, pickme.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
