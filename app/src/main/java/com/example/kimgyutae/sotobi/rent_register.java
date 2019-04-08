package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
<<<<<<< HEAD
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class rent_register extends AppCompatActivity {
    private Spinner spinner_hour;
    ArrayList<String> timeList;
    ArrayAdapter<String> timeAdapter;

    private Spinner spinner_min;
    ArrayList<String> timeList_min;
    ArrayAdapter<String> timeAdapter_min;

=======
import android.widget.Button;

public class rent_register extends AppCompatActivity {
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
=======
import android.widget.Button;

public class rent_register extends AppCompatActivity {
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcanrent);

<<<<<<< HEAD
<<<<<<< HEAD
        timeList = new ArrayList<>();
        timeList.add("0");
        timeList.add("1");
        timeList.add("2");
        timeList.add("3");
        timeList.add("4");
        timeList.add("5");
        timeList.add("6");
        timeList.add("7");
        timeList.add("8");
        timeList.add("9");
        timeList.add("10");
        timeList.add("11");
        timeList.add("12");
        timeList.add("13");
        timeList.add("14");
        timeList.add("15");
        timeList.add("16");
        timeList.add("17");
        timeList.add("18");
        timeList.add("19");
        timeList.add("20");
        timeList.add("21");
        timeList.add("22");
        timeList.add("23");

        timeAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                timeList);

        spinner_hour = (Spinner)findViewById(R.id.rent_hour);
        spinner_hour.setAdapter(timeAdapter);

        timeList_min = new ArrayList<>();
        timeList_min.add("10");
        timeList_min.add("20");
        timeList_min.add("30");
        timeList_min.add("40");
        timeList_min.add("50");

        timeAdapter_min = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                timeList_min);

        spinner_min = (Spinner)findViewById(R.id.rent_min);
        spinner_min.setAdapter(timeAdapter_min);

=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
        Button agreerentBtn = (Button)findViewById(R.id.agreerentBtn);
        agreerentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 대여를 시작한다.
                // 구현 필요
            }
        });
        Button returntorentBtn = (Button)findViewById(R.id.returntorentBtn);
        returntorentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rent_register.this, rent.class);
                startActivity(intent);
                finish();
            }
        });
    }

<<<<<<< HEAD
<<<<<<< HEAD




=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(rent_register.this, rent.class);
        startActivity(intent);
        finish();
    }
}
