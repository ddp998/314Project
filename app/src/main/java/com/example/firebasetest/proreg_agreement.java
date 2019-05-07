package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class proreg_agreement extends AppCompatActivity {
    Button btnAgreeToApproval, btnBack8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proreg_agreement);

        btnAgreeToApproval = findViewById(R.id.btnAgreeToApproval);
        btnBack8 = findViewById(R.id.btnBack8);

        //agree button
        btnAgreeToApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V2) {
                openProDetails();
            }
        });

        //back button
        btnBack8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V2) {
                backToUserTypes();
            }
        });
    }

    public void openProDetails() {
        Intent intent = new Intent(this, proreg_pro_details.class);
        startActivity(intent);
    }

    public void backToUserTypes() {
        Intent intent = new Intent(this, choosing_user_type.class);
        startActivity(intent);
    }
}