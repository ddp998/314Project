package com.example.firebasetest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pro_dashmap extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference();
    DatabaseReference proRef = firebaseDatabase.getReference("Professionals");
    DatabaseReference availRef = firebaseDatabase.getReference("Available Professionals");

    Switch availabilitySwitch;
    //String proEmail, proAvgRating;
    String availId;

    //private String clientId = "";

    Button btnViewRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_dashmap);

        //TODO a logout button (in nav drawer)

        //location services instance
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //availability toggle
        //TODO put switch in nav drawer later
        availabilitySwitch = findViewById(R.id.availabilitySwitch);

        final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        availabilitySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (availabilitySwitch.getText().equals("Unavailable")) {
                    availabilitySwitch.setText("Available");
                    //pro is now available
                    proRef.child(currentUid).child("status").setValue(Status.AVAILABLE);

                    /* TODO get this to actually work to implement showing more information about the professional to the client
                    proRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //get unique email of available professional
                            proEmail = dataSnapshot.child(currentUid).child("email").getValue(String.class);
                            //proAvgRating = dataSnapshot.child(currentUid).child("avgRating").getValue(String.class);


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            //
                        }
                    });
                    */

                    //darling phone 109060

                    availId = availRef.push().getKey();
                    availRef.child(availId).child("professional user id").setValue(currentUid);
                    //availRef.child(availId).child("avgRating").setValue(proAvgRating);
                    availRef.child(availId).child("latitude").setValue(currentLocation.getLatitude());
                    availRef.child(availId).child("longitude").setValue(currentLocation.getLongitude());
                } else if (availabilitySwitch.getText().equals("Available")) {
                    availabilitySwitch.setText("Unavailable");
                    //pro is now unavailable
                    proRef.child(currentUid).child("status").setValue(Status.UNAVAILABLE);
                    //removes pro from "available professionals"
                    availRef.child(availId).removeValue();
                }
            }
        });

        btnViewRequests = findViewById(R.id.btnViewRequests);
        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRequestList();
            }
        });
    }

    private void openRequestList() {
        //TODO make this a popup dialog later
        Intent intent = new Intent(this, pro_request_list.class);
        startActivity(intent);
    }

    @Override
        public void onMapReady (GoogleMap googleMap){
            map = googleMap;
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            googleMap.addMarker(markerOptions);
        }

        private void fetchLastLocation () {
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
                        currentLocation = location;

                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.promap);
                        supportMapFragment.getMapAsync(pro_dashmap.this);
                    }
                }
            });
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            switch (requestCode) {
                case REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        fetchLastLocation();
                        break;
                    }
            }
        }
    }
