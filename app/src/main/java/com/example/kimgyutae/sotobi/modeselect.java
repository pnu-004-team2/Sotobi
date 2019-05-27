
package com.example.kimgyutae.sotobi;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class modeselect extends AppCompatActivity {
    public static String UserID;
    public static String Using_Point;
    public static String uPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modeselect);

        // 에러
        ImageView errorreport = (ImageView)findViewById(R.id.error_report);
        errorreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, errorreport.class);
                startActivity(intent);
            }
        });

        // 내 정보
        ImageView myinfo = (ImageView)findViewById(R.id.my_info);
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, myinfo.class);
                startActivity(intent);
            }
        });
        // 대여
        ImageButton rent = (ImageButton)findViewById(R.id.gotorent);
        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (!success) {
                                Intent intent = new Intent(modeselect.this, rent.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(modeselect.this, renting.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                rent_completeRequest rent_completerequest = new rent_completeRequest(UserID, "PW", responseListener);
                RequestQueue queue = Volley.newRequestQueue(modeselect.this);
                queue.add(rent_completerequest);
            }
        });
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    uPoint = jsonResponse.getString("point");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        pointRequest pointrequest = new pointRequest(UserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(modeselect.this);
        queue.add(pointrequest);

        // 승차 요청
        ImageButton pickme = (ImageButton)findViewById(R.id.gotopickme);
        pickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickme.class);
                startActivity(intent);
            }
        });
        // 동승 찾기
        ImageButton pickup = (ImageButton)findViewById(R.id.gotopickup);
        pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(modeselect.this, pickup.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(modeselect.this, login.class);
        startActivity(intent);
        finish();
    }
}

