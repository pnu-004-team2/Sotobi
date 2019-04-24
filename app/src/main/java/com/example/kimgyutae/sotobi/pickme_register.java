package com.example.kimgyutae.sotobi;

/**
 * Created by kjh on 08/04/2019.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class pickme_register extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme_register);

        // 승차 신청 버튼
        Button agreeBtn = (Button)findViewById(R.id.pickme_agreeBtn);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pickme_register.this, pickme_complete_ready.class);
                startActivity(intent);
                finish();
            }
        });

        // 승차 취소 버튼
        Button returntopickmeBtn = (Button)findViewById(R.id.returntopickmeBtn);
        returntopickmeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pickme_register.this, pickme.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(pickme_register.this, pickme.class);
        startActivity(intent);
        finish();
    }

}


