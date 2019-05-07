package com.example.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.firebasetest.proreg_cert_details.certs;

public class pro_request_list extends AppCompatActivity {
    private ListView bbb;
    private RecyclerView aRequestRecycler;
    private RecyclerView.Adapter aRequestAdapter; //bridge between array items and recyclerview
    private RecyclerView.LayoutManager layoutManager;

    DatabaseReference aReqDatabase;

    private static final String TAG = "pro_request_list";

    Button btnBackToProDashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_request_list);

        // [START initialize_database_ref]
        aReqDatabase = FirebaseDatabase.getInstance().getReference("Active Requests");
        // [END initialize_database_ref]

        aRequestRecycler = findViewById(R.id.requestRecycler);
        aRequestRecycler.setLayoutManager(new LinearLayoutManager(this));

        aReqDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ActiveRequest> activeRequests = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ActiveRequest ar = ds.getValue(ActiveRequest.class);
                    activeRequests.add(ar);
                }
                RequestAdapter adapter = new RequestAdapter(pro_request_list.this, activeRequests);
                aRequestRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(pro_request_list.this, "NETWORK ERROR: Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToProDashMap = findViewById(R.id.btnBackToProDashMap);
        btnBackToProDashMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProDashmap();
            }
        });
    }

    private void openProDashmap() {
        Intent intent = new Intent(this, pro_dashmap.class);
        startActivity(intent);
    }

    //TODO refresh button, "No requests to display" if no active requests currently
}

