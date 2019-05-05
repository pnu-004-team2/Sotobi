package com.example.kimgyutae.sotobi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class pickup extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);

    ArrayList<Marker> markerList = new ArrayList();
    ArrayList<String> destList = new ArrayList();
    ArrayList<String> pointList = new ArrayList();
    ArrayList<String> matchedList = new ArrayList();

    Timer mTimer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("Show");
                            for(Marker marker:markerList){marker.setMap(null);}
                            markerList.clear();
                            destList.clear();
                            pointList.clear();


                            for(int i=0;i<jsonArray.length();i++){
                                try{
                                    JSONObject item = jsonArray.getJSONObject(i);

                                    double lat = Double.parseDouble(item.getString("Latitude"));
                                    double lng = Double.parseDouble(item.getString("Longitude"));
                                    String dest = URLDecoder.decode(item.getString("Dest"), "utf-8");
                                    String give_point = item.getString("Give_Point");
                                    String matched = item.getString("Matched");


                                    markerList.add(new Marker(new LatLng(lat, lng)));
                                    destList.add(dest);
                                    pointList.add(give_point);
                                    matchedList.add(matched);
                                }
                                catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            mapFragment.getMapAsync(pickup.this); // 지도 준비된 것 동기화
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                pickupRequest pickuprequest = new pickupRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(pickup.this);
                queue.add(pickuprequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,10000);



        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        if(ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) ==
                PackageManager.PERMISSION_GRANTED ){
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                        new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
            }
            mapFragment.getMapAsync(this); // 지도 준비된 것 동기화

        }
        else{
            int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = PackageManager.PERMISSION_GRANTED;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            if(ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) ==
                    PackageManager.PERMISSION_GRANTED ){
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (mapFragment == null) {
                    mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                            new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                    getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
                }
                mapFragment.getMapAsync(this); // 지도 준비된 것 동기화

            }
        }
    }

    // 위치 사용 허용 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 지도에 띄울 것 준비 (메인)
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        for(final Marker marker:markerList){
            final int index = markerList.indexOf(marker);

                marker.setIcon(MarkerIcons.YELLOW);
                marker.setMap(naverMap);
                // 각 마커에 대한 오토바이 픽업 요청 승인하기
                marker.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        Intent intent = new Intent(pickup.this, pickup_check.class); // 픽업하러 가겠다는 창 표시 필요
                        intent.putExtra("Lat", Double.toString(marker.getPosition().latitude));
                        intent.putExtra("Lng", Double.toString(marker.getPosition().longitude));
                        intent.putExtra("Dest", destList.get(index));
                        intent.putExtra("Get_Point", pointList.get(index));
                        startActivity(intent);
                        mTimer.cancel();
                        finish();
                        return false;
                    }
                });

        }
    }

    @Override
    public void onBackPressed() {
        finish();
        mTimer.cancel();
    }
}
