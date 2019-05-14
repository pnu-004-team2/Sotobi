package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

/**
 * Created by Jeong Jae Gwang on 2019-04-21.
 */

public class myinfo extends AppCompatActivity {

    String id = "";
    String curpassword = "";
    String email = "";
    String phonenumber = "";
    String name = "";
    String motormodel = "";
    String motornumber = "";
    String point = "";
    String comparePass = "";
    String newpassword = "";
    boolean passOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        Intent loginInfo = getIntent();
        // 아이디 불러오기
        final String loginId = UserID;
        /*TextView myinfo_id = (TextView)findViewById(R.id.myinfo_id);
        myinfo_id.setText(loginId);
        // 이름 불러오기
        final String loginName = loginInfo.getStringExtra("name");
        TextView myinfo_name = (TextView)findViewById(R.id.myinfo_name);
        myinfo_name.setText(loginName);
        */
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    id = jsonResponse.getString("id");
                    curpassword = jsonResponse.getString("password");
                    email = jsonResponse.getString("email");
                    phonenumber = jsonResponse.getString("phonenumber");
                    name = URLDecoder.decode(jsonResponse.getString("name"),"utf-8");
                    motormodel = URLDecoder.decode(jsonResponse.getString("motormodel"),"utf-8");
                    motornumber = jsonResponse.getString("motornumber");
                    point = jsonResponse.getString("point");

                    TextView myinfo_id = (TextView)findViewById(R.id.myinfo_id);
                    myinfo_id.setText(id);
                    TextView myinfo_email = (TextView)findViewById(R.id.myinfo_email);
                    myinfo_email.setText(email);
                    TextView myinfo_phonenumber = (TextView)findViewById(R.id.myinfo_phonenumber);
                    myinfo_phonenumber.setText(phonenumber);
                    TextView myinfo_name = (TextView)findViewById(R.id.myinfo_name);
                    myinfo_name.setText(name);
                    TextView myinfo_motormodel = (TextView)findViewById(R.id.myinfo_motormodel);
                    myinfo_motormodel.setText(motormodel);
                    TextView myinfo_motornumber = (TextView)findViewById(R.id.myinfo_motornumber);
                    myinfo_motornumber.setText(motornumber);
                    TextView myinfo_point = (TextView)findViewById(R.id.myinfo_point);
                    myinfo_point.setText(point);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        infoRequest inforequest = new infoRequest(loginId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(myinfo.this);
        queue.add(inforequest);
        // 뒤로가기 버튼

        Button passCheckBtn = (Button)findViewById(R.id.myinfo_checkPass);
        passCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText myinfo_pass = (EditText)findViewById(R.id.myinfo_password);
                EditText myinfo_newPass = (EditText)findViewById(R.id.myinfo_newPass);
                comparePass = myinfo_pass.getText().toString();
                if(comparePass.equals(curpassword)) {
                    Toast.makeText(getApplicationContext(),"확인 되었습니다.", Toast.LENGTH_SHORT).show();
                    passOk = true;
                    myinfo_newPass.requestFocus();
                    myinfo_pass.setFocusableInTouchMode(false);
                }
                else
                    Toast.makeText(getApplicationContext(),"비밀번호 미일치", Toast.LENGTH_SHORT).show();
            }
        });
        //
        Button changePass = (Button)findViewById(R.id.myinfo_changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passOk)
                {
                    EditText myinfo_newPass = (EditText)findViewById(R.id.myinfo_newPass);
                    newpassword = myinfo_newPass.getText().toString();
                    if(newpassword.length() > 4 && !newpassword.equals(newpassword.toLowerCase()) && !newpassword.equals(newpassword.toUpperCase()) && Pattern.matches("^[0-9a-zA-Z가-힣]*$", newpassword))
                    {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),"변경 되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        };
                        passChangeRequest passrequest = new passChangeRequest(loginId,newpassword, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(myinfo.this);
                        queue.add(passrequest);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "비밀번호는 대/소문자, 숫자를 혼합하여 5자이상 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"기존 비밀번호 확인", Toast.LENGTH_SHORT).show();
            }
        });
        Button regiBtn = (Button)findViewById(R.id.myinfo_change);
        regiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myinfo.this, vehicle_register.class);
                intent.putExtra("id",loginId);
                startActivity(intent);
                finish();
            }
        });


        Button cancelBtn = (Button)findViewById(R.id.myinfo_back);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}