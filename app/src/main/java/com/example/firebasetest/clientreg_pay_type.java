package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// choosing payment type
public class clientreg_pay_type extends AppCompatActivity {
    Button btnSubscription;
    Button btnPayPerUse;
    Button btnBack4;

    static PaymentType paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_pay_type);

        btnSubscription = findViewById(R.id.btnSubscription);
        btnPayPerUse = findViewById(R.id.btnPayPerUse);
        btnBack4 = findViewById(R.id.btnBack4);

        btnSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                paymentType = PaymentType.SUB;
                openPayMethod();
            }
        });

        btnPayPerUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                paymentType = PaymentType.PERUSE;
                openPayMethod();
            }
        });

        btnBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                backToCarDetails();
            }
        });
    }

    public void openPayMethod() {
        Intent intent = new Intent(this, clientreg_pay_method.class);
        startActivity(intent);
    }

    public void backToCarDetails() {
        Intent intent = new Intent(this, clientreg_car_details.class);
        startActivity(intent);
    }
}
