package com.example.kimgyutae.sotobi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class register extends AppCompatActivity {
    private boolean idCheckValue = false;
    private boolean AuthDone = false;
    private EditText et_email;
    private String Auth;
    private boolean reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_email = (EditText)findViewById(R.id.register_email);

        reg = false;
        Auth = new String("");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        final EditText rpn = (EditText)findViewById(R.id.register_person_name);
        final EditText et_id = (EditText)findViewById(R.id.register_id);

        // 아이디 확인 버튼
        Button checkBtn = (Button)findViewById(R.id.id_check);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String input_id = et_id.getText().toString();
                if( Pattern.matches("^[0-9a-zA-Z가-힣]*$", input_id)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    if(input_id.length() > 4) {
                                        Toast.makeText(getApplicationContext(), "생성 가능한 아이디 입니다", Toast.LENGTH_SHORT).show();
                                        idCheckValue = true;
                                        et_id.setClickable(false);
                                        et_id.setFocusable(false);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "아이디 5자 이상 입력하세요", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "아이디 중복입니다", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    DupcheckRequest dupcheckrequest = new DupcheckRequest(input_id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(register.this);
                    queue.add(dupcheckrequest);
                }
                else{
                    Toast.makeText(getApplicationContext(), "ID는 영문자와 숫자를 혼합하여 입력하세요", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 이메일 확인 버튼
        final Button emailBtn = (Button)findViewById(R.id.email_check);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AuthDone){
                    String mailadd = et_email.getText().toString() + "@pusan.ac.kr";
                    String mailcontent = "Sotobi 회원가입을 위한 인증 메일입니다. - 인증 문자 : ";
                    if(mailadd.length()<=12){
                        Toast.makeText(getApplicationContext(), "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            MailSender gMailSender = new MailSender("zz5473@pusan.ac.kr", "Redsky76^");
                            Auth = gMailSender.getEmailCode();
                            mailcontent+=Auth;
                            //GMailSender.sendMail(제목, 본문내용, 받는사람);
                            gMailSender.sendMail("[Sotobi]회원가입 인증 메일", mailcontent, mailadd);
                            Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                        } catch (SendFailedException e) {
                            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                        } catch (MessagingException e) {
                            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        // 인증번호 확인
        final Button codeBtn = (Button)findViewById(R.id.code_check);
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!AuthDone){
                    EditText et_code = (EditText)findViewById(R.id.register_code);
                    if(et_code.getText().toString().equals(Auth)){
                        Toast.makeText(getApplicationContext(), "인증완료", Toast.LENGTH_SHORT).show();
                        codeBtn.setText("완료");
                        et_code.setFocusableInTouchMode(false);
                        et_email.setFocusableInTouchMode(false);
                        emailBtn.setText("완료");
                        AuthDone = true;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "인증실패", Toast.LENGTH_SHORT).show();
                    }

                }
             }
        });

        // 등록 버튼
        Button makeBtn = (Button)findViewById(R.id.register_makeBtn);
        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_pw = (EditText)findViewById(R.id.register_pw);
                EditText et_pw_same = (EditText)findViewById(R.id.register_pw_same);
                EditText et_pName = (EditText)findViewById(R.id.register_person_name);
                EditText et_phone = (EditText)findViewById(R.id.register_phone);
                EditText et_id = (EditText)findViewById(R.id.register_id);

                String pw = et_pw.getText().toString();
                String pw_same = et_pw_same.getText().toString();
                String pName = et_pName.getText().toString();
                String phone = et_phone.getText().toString();
                String email = et_email.getText().toString();
                String id = et_id.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(register.this, login.class);
                                register.this.startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                if(idCheckValue){
                    if(pw.length() > 0){
                        if(pw.length() > 4 && !pw.equals(pw.toLowerCase()) && !pw.equals(pw.toUpperCase()) && Pattern.matches("^[0-9a-zA-Z가-힣]*$", pw)){
                            if(pw.equals(pw_same)) {
                                if(pName.length() > 0 && Pattern.matches("^[가-힣]*$", pName)){
                                    if(AuthDone){
                                        if(phone.length() > 0){
                                            // 서버 전송
                                            Toast.makeText(getApplicationContext(), "전송", Toast.LENGTH_SHORT).show();
                                            registerRequest registerrequest = new registerRequest(email, id, pName, pw, phone, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(register.this);
                                            queue.add(registerrequest);
                                        }
                                        else Toast.makeText(getApplicationContext(), "전화 번호 입력해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(getApplicationContext(), "이메일 인증해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(getApplicationContext(), "이름을 올바르게 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(), "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(getApplicationContext(), "비밀번호는 대/소문자, 숫자를 혼합하여 5자이상 입력하세요", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "아이디 중복 확인을 해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        // 취소 버튼
        Button cancelBtn = (Button)findViewById(R.id.register_cancleBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
