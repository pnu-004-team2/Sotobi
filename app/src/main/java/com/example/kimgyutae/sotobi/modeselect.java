package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class modeselect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeselect);
        // 대여
        Button rent = (Button)findViewById(R.id.gotorent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, rent.class);
                startActivity(intent);
                finish();
            }
        });
        // 승차 요청
        Button pickme = (Button)findViewById(R.id.gotopickme);
        pickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickme.class);
                startActivity(intent);
<<<<<<< HEAD
=======
<<<<<<< HEAD
<<<<<<< HEAD
                startActivity(intent);
=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
>>>>>>> 30f7fa97580fa91557db300170282cfc37f55c67
                finish();
            }
        });
        // 동승 찾기
        Button pickup = (Button)findViewById(R.id.gotopickup);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
