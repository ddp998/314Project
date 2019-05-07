package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class base_registration extends AppCompatActivity {
    EditText fieldFName; //first name
    EditText fieldLName; //last name
    EditText fieldEmail; //email
    EditText fieldPwd; //password
    EditText fieldPhone; //phone number
    //ProgressBar progressBar;
    Button btnContinueRegister;
    Button btnBack;
    //Button btnRegisterDriver; //register button

    static String firstName, lastName, email, password, phone;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_registration);

        fieldFName = findViewById(R.id.fieldFName);
        fieldLName = findViewById(R.id.fieldLName);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPwd = findViewById(R.id.fieldPwd);
        fieldPhone = findViewById(R.id.fieldPhone);
        //progressBar = findViewById(R.id.progressBar);
        btnContinueRegister = findViewById(R.id.btnContinueRegister);
        btnBack = findViewById(R.id.btnBack);
        //btnRegisterClient = findViewById(R.id.btnRegisterClient); //demo reg
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        //check if user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            openDashMap();
        } */

        btnContinueRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                boolean allValidFields = true;

                String attemptFN = fieldFName.getText().toString().trim();
                String attemptLN = fieldLName.getText().toString().trim();
                String attemptEmail = fieldEmail.getText().toString().trim();
                String attemptPwd = fieldPwd.getText().toString().trim();
                String attemptPhone = fieldPhone.getText().toString().trim();

                //FIRST NAME VALIDATION
                String[] fNameLetters = attemptFN.split("");
                if (isAllLetters(fNameLetters) && !attemptFN.equals("")) {
                    firstName = fieldFName.getText().toString().trim();
                } else if (!isAllLetters(fNameLetters) || attemptFN.equals("")) {
                    Toast.makeText(base_registration.this,
                            "Please enter a valid first name.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //LAST NAME VALIDATION
                String[] lNameLetters = attemptLN.split("");
                if (isAllLetters(lNameLetters) && !attemptLN.equals("")) {
                    lastName = fieldLName.getText().toString().trim();
                } else if (!isAllLetters(lNameLetters) || attemptLN.equals("")) {
                    Toast.makeText(base_registration.this,
                            "Please enter a valid last name.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }
                //EMAIL VALIDATION
                if (attemptEmail.equals("") || !isEmailValid(attemptEmail)) {
                    Toast.makeText(base_registration.this,
                            "Please enter a valid email.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else if (!attemptEmail.equals("") && isEmailValid(attemptEmail)) {
                    email = fieldEmail.getText().toString().trim();
                }
                //PASSWORD VALIDATION
                String[] pwdChars = attemptPwd.split("");
                if (attemptPwd.equals("") || !isValidPwd(pwdChars)) {
                    Toast.makeText(base_registration.this,
                            "A password must be at least 6 characters long.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else if (!attemptPwd.equals("") && isValidPwd(pwdChars)) {
                    password = fieldPwd.getText().toString().trim();
                }
                //PHONE VALIDATION
                String[] phnNumbers = attemptPhone.split("");
                if (!isValidPhone(phnNumbers)) {
                    Toast.makeText(base_registration.this,
                            "A phone number must be 10 digits long.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else if (attemptPhone.equals("")) {
                    Toast.makeText(base_registration.this,
                            "Please enter a valid phone number.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else if (!attemptPhone.equals("") || isValidPhone(phnNumbers)) {
                    phone = fieldPhone.getText().toString();
                }

                if (allValidFields) {
                    openChooseUserType();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V2) {
                backToLogin();
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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isValidPwd(String[] str) {
        boolean isValidPwd = false;
        int pwdCharCounter = 0;

        for (int i = 0; i < str.length; i++) {
            pwdCharCounter++;
        }

        if (pwdCharCounter >= 6) {
            isValidPwd = true;
        }

        return isValidPwd;
    }

    public boolean isValidPhone(String[] str) {
        boolean isValidPhone = true;
        int checked;
        int digitCount = 0;

        for (int i = 1; i < str.length; i++) {
            try {
                checked = Integer.parseInt(str[i]);
                digitCount++;

                if (digitCount == 10) {
                    isValidPhone = true;
                } else {
                    isValidPhone = false;
                }
            } catch (NumberFormatException | NullPointerException e) {
                e.printStackTrace();
                isValidPhone = false;
                break;
            }

        }

        return isValidPhone;
    }

    //open choosing user type activity
    public void openChooseUserType() {
        Intent intent = new Intent(this, choosing_user_type.class);
        startActivity(intent);
    }

    //go back to login screen
    public void backToLogin() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    //user is already logged in, open dashboard map
    public void openDashMap() {
        Intent intent = new Intent(this, client_dashmap.class);
        startActivity(intent);
    }
}