package com.example.kimgyutae.sotobi;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText lg_id = (EditText)findViewById(R.id.login_id);
        final EditText lg_pw = (EditText)findViewById(R.id.login_pw);

        // 로그인 버튼
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = lg_id.getText().toString();
                final String password = lg_pw.getText().toString();

                if(id.length()<1){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<1){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (!success) {
                                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonResponse2 = new JSONObject(response);
                                                boolean success = jsonResponse2.getBoolean("success");

                                                if (success) {
                                                    String gid = jsonResponse2.getString("id");
                                                    String name = jsonResponse2.getString("name");

                                                    Intent intent = new Intent(login.this, modeselect.class);
                                                    intent.putExtra("id", gid);
                                                    intent.putExtra("name", name);
                                                    login.this.startActivity(intent);

                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "로그인 실패! 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                                    loginRequest loginrequest = new loginRequest(id, password, responseListener2);
                                    RequestQueue queue = Volley.newRequestQueue(login.this);
                                    queue.add(loginrequest);

                                } else {
                                    Toast.makeText(getApplicationContext(), "로그인 실패! 아이디를 확인하세요", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DupcheckRequest dupcheckrequest = new DupcheckRequest(id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(login.this);
                    queue.add(dupcheckrequest);
                }
            }
        });
        // 회원가입 버튼
        Button regiBtn = (Button)findViewById(R.id.regiBtn);
        regiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
