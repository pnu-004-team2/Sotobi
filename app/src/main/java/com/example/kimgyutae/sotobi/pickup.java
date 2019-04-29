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
import android.widget.Toast;

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

import java.util.ArrayList;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class pickup extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);

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

        ArrayList<Marker> markerList = new ArrayList();

        markerList.add(new Marker(new LatLng(35.2318263, 129.0825006)));
        markerList.add(new Marker(new LatLng(35.2372553, 129.0873740)));
        markerList.add(new Marker(new LatLng(35.2376547, 129.0836640)));
        markerList.add(new Marker(new LatLng(35.2372602, 129.0845680)));

        for(Marker marker:markerList){
            marker.setIcon(MarkerIcons.YELLOW);
            marker.setMap(naverMap);
            // 각 마커에 대한 오토바이 픽업 요청 승인하기
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    Intent intent = new Intent(pickup.this, modeselect.class); // 픽업하러 가겠다는 창 표시 필요
                    startActivity(intent);
                    finish();
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
