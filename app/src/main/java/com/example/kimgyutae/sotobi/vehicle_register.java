package com.example.kimgyutae.sotobi;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *  Created by hdy 19-04-21
 */
public class vehicle_register extends AppCompatActivity {

    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_do = "";
    String choice_se = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleregister);

        final Spinner spin1 = (Spinner)findViewById(R.id.vehicle_company);
        final Spinner spin2 = (Spinner)findViewById(R.id.vehicle_model);

        adspin1 = ArrayAdapter.createFromResource(this, R.array.company, R.layout.spinner_layout);
        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adspin1.getItem(i).equals("--------")){
                    // 초기값 지정
                    choice_do = "--------";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.empty, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("KR Motors")){
                    choice_do = "KR Motors";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.KR_Motors, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Daelim")){
                    choice_do = "Daelim";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Daelim, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Honda")){
                    choice_do = "Honda";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Honda, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Sym")){
                    choice_do = "Sym";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.SYM, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Vespa")){
                    choice_do = "Vespa";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Vespa, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Italjet")){
                    choice_do = "Italjet";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Italjet, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if (adspin1.getItem(i).equals("Suzuki")){
                    choice_do = "Suzuki";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Suzuki, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if (adspin1.getItem(i).equals("Yamaha")){
                    choice_do = "Yamaha";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Yamaha, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if (adspin1.getItem(i).equals("Kymco")){
                    choice_do = "Kymco";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Kymco, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else if (adspin1.getItem(i).equals("Piaggio")){
                    choice_do = "Piaggio";
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Piaggio, R.layout.spinner_layout);
                    adspin2.setDropDownViewResource(R.layout.spinner_layout);
                    spin2.setAdapter(adspin2);
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        /* 등록 버튼 */
        Button regiBtn = (Button)findViewById(R.id.vehicle_register);
        regiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_number = (EditText) findViewById(R.id.vehicle_number);
                String company = choice_do;
                String model = null;
                try {
                    model = URLEncoder.encode(choice_se,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String number = et_number.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "오토바이 등록 완료", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(vehicle_register.this, myinfo.class);
                                vehicle_register.this.startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if (!model.equals("--------")) {
                    if (number.length() > 0) {// 서버 전송
                        //Toast.makeText(getApplicationContext(), "전송", Toast.LENGTH_SHORT).show();
                        vehicle_registerRequest Vrequest = new vehicle_registerRequest(id,company,model,number, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(vehicle_register.this);
                        queue.add(Vrequest);
                    } else  Toast.makeText(getApplicationContext(), "오토바이 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else  Toast.makeText(getApplicationContext(), "오토바이 모델을 선택해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        /* 취소 버튼 */
        Button cancelBtn = (Button)findViewById(R.id.vehicle_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(vehicle_register.this, myinfo.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(vehicle_register.this, myinfo.class);
        startActivity(intent);
        finish();
    }
}