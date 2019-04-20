package com.example.kimgyutae.sotobi;

/**
 * Created by kjh on 08/04/2019.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class pickme_register extends AppCompatActivity{

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(pickme_register.this, pickme.class);
        startActivity(intent);
        finish();
    }
}


