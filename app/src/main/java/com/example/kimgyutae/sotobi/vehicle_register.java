package com.example.kimgyutae.sotobi;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        adspin1 = ArrayAdapter.createFromResource(this, R.array.company, R.layout.support_simple_spinner_dropdown_item);
        spin1.setAdapter(adspin1);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adspin1.getItem(i).equals("KR Motors")){
                    choice_do = "KR Motors";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.KR_Motors, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Daelim")){
                    choice_do = "Daelim";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Daelim, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Honda")){
                    choice_do = "Honda";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Honda, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Sym")){
                    choice_do = "Sym";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.SYM, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Vespa")){
                    choice_do = "Vespa";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Vespa, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }
                else if (adspin1.getItem(i).equals("Italjet")){
                    choice_do = "Italjet";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Italjet, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }else if (adspin1.getItem(i).equals("Suzuki")){
                    choice_do = "Suzuki";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Suzuki, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }else if (adspin1.getItem(i).equals("Yamaha")){
                    choice_do = "Yamaha";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Yamaha, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }else if (adspin1.getItem(i).equals("Kymco")){
                    choice_do = "Kymco";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Kymco, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
                        }
                    });
                }else if (adspin1.getItem(i).equals("Piaggio")){
                    choice_do = "Piaggio";//버튼 클릭시 출력을 위해 값을 넣었습니다.
                    adspin2 = ArrayAdapter.createFromResource(vehicle_register.this, R.array.Piaggio, android.R.layout.simple_spinner_dropdown_item);//서울일 경우에 두번째 spinner에 값을 넣습니다. //그냥 this가 아닌 Main~~~인 이유는 그냥 this는 메인엑티비티인 경우만 가능합니다. //지금과 같이 다른 함수안이나 다른 클래스에서는 꼭 자신의 것을 넣어주어야 합니다.//혹시나 다른 class -> Public View밑에서 작업하시는 분은 view명.getContext()로 해주셔야 합니다.//예로 View rootView =~~ 선언하신 경우에는 rootView.getContext()써주셔야합니다. this가 아니라요.
                    adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin2.setAdapter(adspin2);//두번째 어댑터값을 두번째 spinner에 넣었습니다.
                    spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//저희는 두번째 선택된 값도 필요하니 이안에 두번째 spinner 선택 이벤트를 정의합니다.
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            choice_se = adspin2.getItem(i).toString();//두번째 선택된 값을 choice_se에 넣습니다.
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {//아무것도 선택안될시 부분입니다. 자동완성됩니다.
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
                String model = choice_se;
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

                if (model.length() > 0) {
                    if (number.length() > 0) {// 서버 전송
                        //Toast.makeText(getApplicationContext(), "전송", Toast.LENGTH_SHORT).show();
                        vehicle_registerRequest Vrequest = new vehicle_registerRequest(id,model,number, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(vehicle_register.this);
                        queue.add(Vrequest);
                    } else  Toast.makeText(getApplicationContext(), "차량 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else  Toast.makeText(getApplicationContext(), "차량 종류를 입력해주세요", Toast.LENGTH_SHORT).show();
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
}