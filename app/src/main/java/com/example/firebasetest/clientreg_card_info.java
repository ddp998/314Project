package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class clientreg_card_info extends AppCompatActivity {
    EditText fieldCardholderName, fieldCardNumber, fieldMonth, fieldYear;
    EditText fieldCvc;

    Button btnContinueToValidation, btnBack6;

    static String ownerFullName, cardNum;
    static Date expiryDate;
    static String securityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_card_info);

        fieldCardholderName = findViewById(R.id.fieldCardholderName);
        fieldCardNumber = findViewById(R.id.fieldCardNumber);
        fieldMonth = findViewById(R.id.fieldMonth);
        fieldYear = findViewById(R.id.fieldYear);
        fieldCvc = findViewById(R.id.fieldCvc);
        btnContinueToValidation = findViewById(R.id.btnContinueToValidation);
        btnBack6 = findViewById(R.id.btnBack6);

        btnContinueToValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allValidFields = true;

                String attemptCardholder = fieldCardholderName.getText().toString();
                String attemptCardNum = fieldCardNumber.getText().toString();
                String attemptMonth = fieldMonth.getText().toString();
                String attemptYear = fieldYear.getText().toString();
                String attemptCvc = fieldCvc.getText().toString();

                //CARDHOLDER NAME VALIDATION
                String[] cardholdLetters = attemptCardholder.split("");
                if (attemptCardholder.equals("") || !isAllLetters(cardholdLetters)) {
                    Toast.makeText(clientreg_card_info.this,
                            "Please enter a valid cardholder name.",
                            Toast.LENGTH_LONG).show();

                    allValidFields = false;
                } else {
                    ownerFullName = attemptCardholder;
                }

                //CARD NUMBER VALIDATION
                String[] cardNumbers = attemptCardNum.split("");
                if (!isAllNumbers(cardNumbers) || attemptCardNum.equals("") || cardNumbers.length != 17) {
                    Toast.makeText(clientreg_card_info.this,
                            "Please enter a valid card number.",
                            Toast.LENGTH_LONG).show();

                    allValidFields = false;
                } else {
                    cardNum = attemptCardNum;
                }

                //EXPIRY DATE VALIDATION
                //

                //SECURITY CODE VALIDATION
                String[] cvcNums = attemptCvc.split("");
                if (!isAllNumbers(cvcNums) || attemptCvc.equals("") || cvcNums.length != 4) {
                    Toast.makeText(clientreg_card_info.this,
                            "Please enter a valid security code.",
                            Toast.LENGTH_LONG).show();

                    allValidFields = false;
                } else {
                    securityCode = attemptCvc;
                }

                if (allValidFields) {
                    endRegistration();
                }
            }
        });

        btnBack6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPaymentMethods();
            }
        });
    }

    public boolean isAllLetters(String[] str) {
        boolean isAllLetters = false;
        int checked;

        for (int i = 0; i < str.length; i++) {
            try {
                checked = Integer.parseInt(str[i]);
                isAllLetters = false;
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                isAllLetters = true;
            }
        }
        return isAllLetters;
    }

    public boolean isAllNumbers(String[] str) {
        boolean isAllNumbers = false;
        int checked;

        for (int i = 0; i < str.length; i++) {
            try {
                checked = Integer.parseInt(str[i]);
                isAllNumbers = true;
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                isAllNumbers = false;
            }
        }
        return isAllNumbers;
    }

    public void endRegistration() {
        Intent intent = new Intent(this, clientreg_end.class);
        startActivity(intent);
    }

    public void backToPaymentMethods() {
        Intent intent = new Intent(this, clientreg_pay_method.class);
        startActivity(intent);
    }
}

// TODO expiry date, add card to database