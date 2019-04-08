package com.example.kimgyutae.sotobi;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
<<<<<<< HEAD
=======
<<<<<<< HEAD
<<<<<<< HEAD

>>>>>>> 30f7fa97580fa91557db300170282cfc37f55c67
                final String id = lg_id.getText().toString();
                final String password = lg_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String gid = jsonResponse.getString("id");
                                String name = jsonResponse.getString("name");

                                Intent intent = new Intent(login.this, modeselect.class);
                                intent.putExtra("id", gid);
                                intent.putExtra("name", name);
                                login.this.startActivity(intent);

                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                loginRequest loginrequest = new loginRequest(id, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login.this);
                queue.add(loginrequest);

                //Intent intent = new Intent(login.this, mainmenu.class);
                //startActivity(intent);
<<<<<<< HEAD
=======
=======
=======
>>>>>>> 335e8f11cbf5986ecb294ce215874bc9328f083a
>>>>>>> 30f7fa97580fa91557db300170282cfc37f55c67
                Intent intent = new Intent(login.this, modeselect.class);
                startActivity(intent);
                finish();
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
