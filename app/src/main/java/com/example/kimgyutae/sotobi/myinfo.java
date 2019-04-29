package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jeong Jae Gwang on 2019-04-21.
 */

public class myinfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        Intent loginInfo = getIntent();
        // 아이디 불러오기
        final String loginId = loginInfo.getStringExtra("id");
        TextView myinfo_id = (TextView)findViewById(R.id.myinfo_id);
        myinfo_id.setText(loginId);
        // 이름 불러오기
        final String loginName = loginInfo.getStringExtra("name");
        TextView myinfo_name = (TextView)findViewById(R.id.myinfo_name);
        myinfo_name.setText(loginName);

        // 뒤로가기 버튼
        Button cancelBtn = (Button)findViewById(R.id.myinfo_back);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}