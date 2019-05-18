package com.example.kimgyutae.sotobi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationResult;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class rent extends AppCompatActivity implements OnMapReadyCallback {
    public static double rLong;
    public static double rLat;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;

    MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);

    ArrayList<Marker> markerList = new ArrayList();
    Timer mTimer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

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

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject item = jsonArray.getJSONObject(i);

                                double lat = Double.parseDouble(item.getString("Latitude"));
                                double lng = Double.parseDouble(item.getString("Longitude"));

                                markerList.add(new Marker(new LatLng(lat, lng)));

                            }
                            mapFragment.getMapAsync(rent.this); // 지도 준비된 것 동기화
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                rentRequest rentrequest = new rentRequest(responseListener);
                RequestQueue queue = Volley.newRequestQueue(rent.this);
                queue.add(rentrequest);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask,0,10000);


        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ){
            List<String> providers = lm.getProviders(true);
            Location location = null;
            for (String provider : providers) {
                Location l = lm.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (location == null || l.getAccuracy() < location.getAccuracy()) {
                    // Found best last known location: %s", l);
                    location = l;
                }
            }
            if (mapFragment == null && (location != null)) {
                mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                        new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
                mapFragment.getMapAsync(this); // 지도 준비된 것 동기화
            }

        }
        else{
            int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = PackageManager.PERMISSION_GRANTED;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            if(ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ){
                List<String> providers = lm.getProviders(true);
                Location location = null;
                for (String provider : providers) {
                    Location l = lm.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (location == null || l.getAccuracy() < location.getAccuracy()) {
                        // Found best last known location: %s", l);
                        location = l;
                    }
                }
                if (mapFragment == null && (location != null)) {
                    mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                            new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                    getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
                    mapFragment.getMapAsync(this); // 지도 준비된 것 동기화
                }

            }
        }
        // 지도 띄우기
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
            marker.setMap(naverMap);
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    Intent intent = new Intent(rent.this, rent_register.class);
                    rLong = marker.getPosition().longitude;
                    rLat = marker.getPosition().latitude;

                    intent.putExtra("Lat", Double.toString(marker.getPosition().latitude));
                    intent.putExtra("Lng", Double.toString(marker.getPosition().longitude));
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
