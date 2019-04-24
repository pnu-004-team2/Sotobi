package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class pickme_complete_ready extends AppCompatActivity {
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pickme_complete);

            // 승차 취소 버튼
            Button returntopickmeBtn = (Button)findViewById(R.id.returntopickmeBtn);
            returntopickmeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(pickme_complete_ready.this, pickme.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
}
