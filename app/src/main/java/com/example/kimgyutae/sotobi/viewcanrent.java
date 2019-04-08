package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class viewcanrent extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcanrent);

        Button agreerentBtn = (Button)findViewById(R.id.agreerentBtn);
        agreerentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewcanrent.this, rent.class);
                startActivity(intent);
                finish();
            }
        });
        Button returntorentBtn = (Button)findViewById(R.id.returntorentBtn);
        returntorentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewcanrent.this, rent.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(viewcanrent.this, rent.class);
        startActivity(intent);
        finish();
    }
}
