package com.example.kimgyutae.sotobi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;

/**
 * Created by KimGyuTae on 2019-04-02.
 */

public class pickme extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    Marker marker = new Marker();
    @NonNull LatLng LatandLng;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickme);

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
            if (mapFragment == null) {
                mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                        new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
            }

            mapFragment.getMapAsync(this); // 지도 준비된 것 동기화

            locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        }
        else{
            int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = PackageManager.PERMISSION_GRANTED;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            if(ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
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
                if (mapFragment == null) {
                    mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                            new LatLng(location.getLatitude(), location.getLongitude()), NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
                    getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
                }

                mapFragment.getMapAsync(this); // 지도 준비된 것 동기화

                locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }

        Button agreepickmeBtn = (Button)findViewById(R.id.agreepickmeBtn);
        agreepickmeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LatandLng != null) {
                    Intent intent = new Intent(pickme.this, pickme_register.class);
                    intent.putExtra("Lat",Double.toString(LatandLng.latitude));
                    intent.putExtra("Lng",Double.toString(LatandLng.longitude));
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "승차 위치를 찍어주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                double longitude = location.getLongitude();

                double latitude = location.getLatitude();

                float accuracy = location.getAccuracy();

            } else {

            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

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
    public void onMapReady(@NonNull final NaverMap naverMap) {
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                marker.setMap(null);
                marker.setPosition(latLng);
                LatandLng = latLng;
                marker.setMap(naverMap);
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
