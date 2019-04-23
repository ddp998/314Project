package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class clientreg_end extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    Button btnStartApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_end);

        btnStartApp = findViewById(R.id.btnStartApp);

        // button to start using app
        btnStartApp = findViewById(R.id.btnStartApp);
        btnStartApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                openDashMap();
            }
        });
    }

    public void openDashMap() {
        Intent intent = new Intent(this, mapgoesherelol.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

//TODO probably a more informative screen