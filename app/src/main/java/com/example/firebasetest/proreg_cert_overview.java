package com.example.firebasetest;

import android.app.ListActivity;

import static com.example.firebasetest.base_registration.email;
import static com.example.firebasetest.base_registration.firstName;
import static com.example.firebasetest.base_registration.lastName;
import static com.example.firebasetest.base_registration.password;
import static com.example.firebasetest.base_registration.phone;
import static com.example.firebasetest.proreg_cert_details.certs;
import static com.example.firebasetest.proreg_pro_details.companyName;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class proreg_cert_overview extends ListActivity {
    private ListView aaa;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter; //bridge between array items and recyclerview
    private RecyclerView.LayoutManager mLayoutManager;

    Button btnAddAnotherCert;
    Button btnContinueProReg2;

    ProgressBar progressBar4;

    FirebaseAuth firebaseAuth;
    DatabaseReference CertDatabase;

    private static final String TAG = "proreg_cert_overview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proreg_cert_overview);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar4 = findViewById(R.id.progressBar4);

        // [START initialize_database_ref]
        CertDatabase = FirebaseDatabase.getInstance().getReference("Certifications");
        // [END initialize_database_ref]

        ArrayList<CertItem> certItemList = new ArrayList<>();
        for (Certification c : certs) {
            certItemList.add(new CertItem(R.drawable.ic_star, c.getCertName(), c.getIssuedBy()));
        }

        aaa = findViewById(android.R.id.list);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CertAdapter(certItemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //add another cert
        btnAddAnotherCert = findViewById(R.id.btnAddAnotherCert);
        btnAddAnotherCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCertDetails();
            }
        });

        //continue button
        btnContinueProReg2 = findViewById(R.id.btnContinueProReg2);
        btnContinueProReg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeProRegistration();
                //openProRegEnd();
            }
        });
    }

    public void openCertDetails() {
        Intent intent = new Intent(this, proreg_cert_details.class);
        startActivity(intent);
    }

    public void completeProRegistration() {
        //String test = email + " " + password;
        //Toast.makeText(proreg_cert_overview.this, test, Toast.LENGTH_LONG).show();

        progressBar4.setVisibility(View.VISIBLE);
        // add user to database
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //check if task is successful
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Professional pro = new Professional(firstName, lastName, email, phone,0, companyName, certs, Status.UNAVAILABLE, 0);

                    FirebaseDatabase.getInstance().getReference("Professionals")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(pro).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //add cert to database
                                addNewCertToDatabase();

                                Toast.makeText(proreg_cert_overview.this, "Registration complete.", Toast.LENGTH_LONG).show();
                                //verification
                                sendEmailVerification();

                                openProRegEnd();
                            } else {
                                Toast.makeText(proreg_cert_overview.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void addNewCertToDatabase() {
        for (Certification c: certs) {
            String id = CertDatabase.push().getKey();
            CertDatabase.child(id).setValue(c);
        }
    }

    //email verification
    public void sendEmailVerification() {
        // [START send_email_verification]
        FirebaseUser user = firebaseAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Verification email sent.");
                        }
                    }
                });
        // [END send_email_verification]
    }

    public void openProRegEnd() {
        Intent intent = new Intent(this, proreg_end.class);
        startActivity(intent);
    }
}
