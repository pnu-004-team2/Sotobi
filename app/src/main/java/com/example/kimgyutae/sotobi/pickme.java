package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class pickme extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(pickme.this, modeselect.class);
        startActivity(intent);
        finish();
    }
}
