package com.example.firebasetest;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapgoesherelol extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapgoesherelol);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng UOW = new LatLng(-34.4054, 150.8784);
        map.addMarker(new MarkerOptions().position(UOW).title("University of Wollongong"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(UOW, 12.0f));
    }
}

//TODO put the marker at client location, sending requests from client to professional
/*
* set zoom scale - d
* get location (allow location services) or prompt warning for manual
* request class (request id 6 digits, location, problem, details, user, urgency level, activity status)
* ui stuff
 */