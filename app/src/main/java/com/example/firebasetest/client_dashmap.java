package com.example.firebasetest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class client_dashmap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    Location clientLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    Button btnRequestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashmap);

        final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //location services instance
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //TODO a logout button (in nav drawer)

        //send request button
        btnRequestService = findViewById(R.id.btnRequestService);
        btnRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request attributes
                String requestId, clientId, clientEmail, clientLat, clientLng;
                ArrayList<Issue> issues;
                String details;

                //TODO proper dialog with fields for request, adding fields to database

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference("Active Requests");
                requestId = requestRef.push().getKey();
                DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference("Clients");
                requestRef.child(requestId).child("clientId").setValue(currentUid);
                clientId = currentUid;

                String strLat = Double.toString(clientLocation.getLatitude());
                String strLong = Double.toString(clientLocation.getLongitude());

                requestRef.child(requestId).child("latitude").setValue(strLat);
                requestRef.child(requestId).child("longitude").setValue(strLong);

                ActiveRequest r = new ActiveRequest(clientId, strLat, strLong);

                //TODO set event listener if text = cancel (ie confirmation window, remove active request from database)
                btnRequestService.setText("Cancel request");



                /*
                GeoFire geoFire = new GeoFire(ref);
                LatLng clientLatLng = new LatLng(clientLocation.getLatitude(), clientLocation.getLongitude());
                geoFire.setLocation(uid, new GeoLocation(clientLatLng.latitude, clientLatLng.longitude), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        //
                    }
                });

                //redundant but ok i guess why not
                map.addMarker(new MarkerOptions()
                        .position(clientLatLng)
                        .title("Request Location"));
            }
        }); */
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng clientLatLng = new LatLng(clientLocation.getLatitude(), clientLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(clientLatLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(clientLatLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(clientLatLng, 12.0f));
        googleMap.addMarker(markerOptions);

        /* UOW manual location setting for testing
        LatLng UOW = new LatLng(-34.4054, 150.8784);
        googleMap.addMarker(new MarkerOptions().position(UOW).title("University of Wollongong"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UOW, 12.0f));
        */
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    clientLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.clientmap);
                    supportMapFragment.getMapAsync(client_dashmap.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }

    /*
    private void sendRequest(String uid) {
        //TODO add repair details
    }*/
}

//TODO share request information from client to professional, nav drawer (dom)