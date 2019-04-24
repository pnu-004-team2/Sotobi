package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class return_rent extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_rent);

        // 완료 버튼
        Button doneBtn = (Button)findViewById(R.id.DoneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(return_rent.this, modeselect.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
