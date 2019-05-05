package com.example.kimgyutae.sotobi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import static com.example.kimgyutae.sotobi.modeselect.UserID;

public class return_rent extends AppCompatActivity {

    private EditText errorcontent;
    private String mailadd;
    private String mailcontent;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_rent);
        errorcontent = (EditText)findViewById(R.id.rent_error);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        final Button Donebtn = (Button)findViewById(R.id.DoneBtn);
        Donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailadd = errorcontent.getText().toString();
                mailcontent = "[Sotobi] 고장 및 에러접수 내용입니다.\n" + "접수자 : ";
                if(mailadd.length()<=0){
                    Toast.makeText(getApplicationContext(), "고장 및 에러신고 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        MailSender gMailSender = new MailSender("zz5473@pusan.ac.kr", "Redsky76^");
                        mailcontent+=UserID;
                        mailcontent+="\n";
                        mailcontent+="접수내용 : ";
                        mailcontent+=mailadd;
                        //GMailSender.sendMail(제목, 본문내용, 받는사람);
                        gMailSender.sendMail("[Sotobi]고장 및 에러신고", mailcontent, "zz5473@pusan.ac.kr");
                        Toast.makeText(getApplicationContext(), "고장 및 에러신고가 정상접수 되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (SendFailedException e) {
                        Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (MessagingException e) {
                        Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "나머지 확인해주십시오", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
