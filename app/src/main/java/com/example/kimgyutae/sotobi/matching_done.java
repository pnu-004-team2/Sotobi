package com.example.kimgyutae.sotobi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static com.example.kimgyutae.sotobi.modeselect.UserID;
import static com.example.kimgyutae.sotobi.modeselect.uPoint;
import static com.example.kimgyutae.sotobi.modeselect.Using_Point;


public class matching_done extends AppCompatActivity {
    int Point_Result;
    String mode;

    float rating_value;
    String rating = "2.5";
    Intent intent;
    String match_id;

    private EditText errorcontent;
    private String mailadd;
    private String mailcontent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_done);


        errorcontent = (EditText)findViewById(R.id.matching_error);


        intent = getIntent();
        match_id = intent.getStringExtra("match_id");

        Point_Result = Integer.parseInt(uPoint) + Integer.parseInt(Using_Point);
        if((Using_Point.charAt(0)=='-')){
            mode = "pickme";
        }
        else{
            mode = "pickup";
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        TextView Point_View = (TextView)findViewById(R.id.matching_Left_Point);
                        Point_View.setText(Integer.toString(Point_Result));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        history_register_request historyregisterrequest = new history_register_request(UserID, Long.toString(System.currentTimeMillis()),mode,Using_Point,Integer.toString(Point_Result),responseListener);
        LeftPoint_UpdateRequest inforequest = new LeftPoint_UpdateRequest(UserID, Integer.toString(Point_Result), responseListener);
        RequestQueue queue = Volley.newRequestQueue(matching_done.this);
        queue.add(inforequest);
        queue.add(historyregisterrequest);




        RatingBar ratingBar = (RatingBar)findViewById(R.id.rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating_value = v;
                rating = String.valueOf(rating_value);
            }
        });


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // 완료 버튼
        Button doneBtn = (Button)findViewById(R.id.matching_DoneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mailadd = errorcontent.getText().toString();
                mailcontent = "[Sotobi] 고장 및 에러접수 내용입니다.\n" + "접수자 : ";
                if(mailadd.length()>0){
                    try {
                        MailSender gMailSender = new MailSender("zz5473@pusan.ac.kr", "Redsky76^");
                        mailcontent+=UserID;
                        mailcontent+="\n";
                        mailcontent+="접수내용 : ";
                        mailcontent+=mailadd;
                        //GMailSender.sendMail(제목, 본문내용, 받는사람);
                        gMailSender.sendMail("[Sotobi]고장 및 에러신고", mailcontent, "zz5473@pusan.ac.kr");
                        Toast.makeText(getApplicationContext(), "에러신고와 평점등록이 정상접수 되었습니다.", Toast.LENGTH_SHORT).show();
                        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");

                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        rating_request ratingRequest = new rating_request(match_id, rating, responseListener2);
                        RequestQueue queue = Volley.newRequestQueue(matching_done.this);
                        queue.add(ratingRequest);

                    } catch (SendFailedException e) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (MessagingException e) {
                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "나머지 확인해주십시오", Toast.LENGTH_SHORT).show();
                    }

                }
                else if(mailadd.length()==0){
                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                Toast.makeText(getApplicationContext(), "평점등록이 에러신고 없이 접수 되었습니다.", Toast.LENGTH_SHORT).show();

                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    rating_request ratingRequest = new rating_request(match_id, rating, responseListener2);
                    RequestQueue queue = Volley.newRequestQueue(matching_done.this);
                    queue.add(ratingRequest);
                }

            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}