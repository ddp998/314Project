package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class proreg_pro_details extends AppCompatActivity {
    EditText fieldCompanyName;
    Button btnContinueProReg3;
    Button btnBackToAgreement;

    static String companyName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proreg_pro_details);

        fieldCompanyName = findViewById(R.id.fieldCompanyName);

        btnContinueProReg3 = findViewById(R.id.btnContinueProReg3);
        btnContinueProReg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fieldCompanyName.getText().toString().equals("")) {
                    companyName = fieldCompanyName.getText().toString();
                }

                openCertDetails();
            }
        });

        btnBackToAgreement = findViewById(R.id.btnBackToAgreement);
        btnBackToAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToAgreement();
            }
        });
    }

    public void openCertDetails() {
        Intent intent = new Intent(this, proreg_cert_details.class);
        startActivity(intent);
    }

    public void backToAgreement() {
        Intent intent = new Intent(this, proreg_agreement.class);
        startActivity(intent);
    }
}

//TODO lmao forgot ABN field