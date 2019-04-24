package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class matching_done extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_done);

        // 완료 버튼
        Button doneBtn = (Button)findViewById(R.id.matching_DoneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(matching_done.this, modeselect.class);
                startActivity(intent);
                finish();
            }
        });
    }
}