package com.example.firebasetest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class login extends AppCompatActivity {
    private EditText fieldLoginEmail, fieldLoginPwd;
    private Button btnLogin;
    private Button btnRegister;
    private ProgressBar progressBar2;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldLoginEmail = findViewById(R.id.fieldLoginEmail);
        fieldLoginPwd = findViewById(R.id.fieldLoginPwd);
        progressBar2 = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        //check if user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            //check for user type then open appropriate dashmap
        } */

        // button to login
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                userLogin();
            }
        });

        // button to register
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                openChooseUserTypeActivity();
            }
        });
    }

    private void userLogin() {
        progressBar2.setVisibility(View.VISIBLE);

        final String attemptEmail = fieldLoginEmail.getText().toString();
        final String attemptPwd = fieldLoginPwd.getText().toString();
        boolean fieldsFilled = true;

        if (attemptEmail.equals("") || attemptPwd.equals("")) {
            fieldsFilled = false;

            progressBar2.setVisibility(View.INVISIBLE);

            Toast.makeText(login.this,
                    "Email and password cannot be empty.",
                    Toast.LENGTH_LONG).show();
        }

        if (fieldsFilled) {
            firebaseAuth.signInWithEmailAndPassword(attemptEmail, attemptPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar2.setVisibility(View.INVISIBLE);

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        final String uid = user.getUid();

                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                        final Query userQuery = databaseRef.orderByChild(uid);
                        userQuery.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                String parentNode = dataSnapshot.getKey();

                                //GETTING THE FUCKING USER TYPE WHICH TOOK 3000 YEARS REEEEEeeee
                                if (parentNode.equals("Clients")) {
                                    openClientDashMap();
                                } else if (parentNode.equals("Professionals")) {
                                    openProDashMap();
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                //
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                                //
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                //
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                //
                            }
                        });
                    } else {
                        progressBar2.setVisibility(View.INVISIBLE);

                        Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void openClientDashMap() {
        Intent intent = new Intent(this, client_dashmap.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openProDashMap() {
        Intent intent = new Intent(this, pro_dashmap.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void openChooseUserTypeActivity() {
        Intent intent = new Intent(this, base_registration.class);
        startActivity(intent);
    }
}
