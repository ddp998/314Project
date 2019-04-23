package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class choosing_user_type extends AppCompatActivity {
    private Button btnClient;
    private Button btnProfess;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_user_type);

        // button to register as a client
        btnClient = (Button) findViewById(R.id.btnClient);
        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                openCarDetails();
            }
        });

        // button to register as a professional
        btnProfess = (Button) findViewById(R.id.btnProfess);
        btnProfess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                openProAgreement();
            }
        });

        // back button
        btnBack = findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                backToBase();
            }
        });
    }

    public void openCarDetails() {
        Intent intent = new Intent(this, clientreg_car_details.class);
        startActivity(intent);
    }

    public void openProAgreement () {
        Intent intent = new Intent(this, proreg_agreement.class);
        startActivity(intent);
    }

    public void backToBase() {
        Intent intent = new Intent(this, base_registration.class);
        startActivity(intent);
    }
}
