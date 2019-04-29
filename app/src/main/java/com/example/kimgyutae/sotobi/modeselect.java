package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class modeselect extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeselect);

        // 아이디, 이름 넘겨주기
        Intent loginInfo = getIntent();
        final String loginId = loginInfo.getStringExtra("id");
        final String loginName = loginInfo.getStringExtra("name");

        // 내 정보
        ImageView myinfo = (ImageView)findViewById(R.id.my_info);
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, myinfo.class);
                intent.putExtra("id", loginId);
                startActivity(intent);
            }
        });
        // 대여
        Button rent = (Button)findViewById(R.id.gotorent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, rent.class);
                startActivity(intent);
            }
        });
        // 승차 요청
        Button pickme = (Button)findViewById(R.id.gotopickme);
        pickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickme.class);
                startActivity(intent);
            }
        });
        // 동승 찾기
        Button pickup = (Button)findViewById(R.id.gotopickup);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(modeselect.this, login.class);
        startActivity(intent);
        finish();
    }
}
