package com.example.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.firebasetest.base_registration.email;
import static com.example.firebasetest.base_registration.firstName;
import static com.example.firebasetest.base_registration.lastName;
import static com.example.firebasetest.base_registration.password;
import static com.example.firebasetest.base_registration.phone;
import static com.example.firebasetest.clientreg_car_details.engine;
import static com.example.firebasetest.clientreg_car_details.licenseNum;
import static com.example.firebasetest.clientreg_car_details.make;
import static com.example.firebasetest.clientreg_car_details.model;
import static com.example.firebasetest.clientreg_car_details.regNum;
import static com.example.firebasetest.clientreg_car_details.series;
import static com.example.firebasetest.clientreg_car_details.year;
import static com.example.firebasetest.clientreg_car_details.transmission;
import static com.example.firebasetest.clientreg_pay_method.paymentMethod;
import static com.example.firebasetest.clientreg_pay_type.paymentType;

public class clientreg_bank_info extends AppCompatActivity {
    EditText fieldAcctName, fieldBankName, fieldAccountNum, fieldBsb;
    Button btnContinueToValidation2, btnBack7;
    ProgressBar progressBar3;

    static String accountName, bankName, accountNum, bsb;

    FirebaseAuth firebaseAuth;
    DatabaseReference CarDatabase;
    DatabaseReference BankDatabase;

    private static final String TAG = "clientreg_bank_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_bank_info);

        // [START initialize_database_ref]
        CarDatabase = FirebaseDatabase.getInstance().getReference("Cars");
        BankDatabase = FirebaseDatabase.getInstance().getReference("Banks");
        // [END initialize_database_ref]

        fieldAcctName = findViewById(R.id.fieldAcctName);
        fieldBankName = findViewById(R.id.fieldBankName);
        fieldAccountNum = findViewById(R.id.fieldAccountNum);
        fieldBsb = findViewById(R.id.fieldBsb);
        btnContinueToValidation2 = findViewById(R.id.btnContinueToValidation2);
        btnBack7 = findViewById(R.id.btnBack7);
        progressBar3 = findViewById(R.id.progressBar3);

        firebaseAuth = FirebaseAuth.getInstance();
        btnContinueToValidation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allValidFields = true;

                String attemptAccountName = fieldAcctName.getText().toString();
                String attemptBankName = fieldBankName.getText().toString();
                String attemptAccountNum = fieldAccountNum.getText().toString();
                String attemptBsb = fieldBsb.getText().toString();

                //NAME VALIDATION
                String[] accountNameLetters = attemptAccountName.split("");
                if (!isAllNumbers(accountNameLetters) && !accountNameLetters.equals("")) {
                    accountName = attemptAccountName;
                } else {
                    Toast.makeText(clientreg_bank_info.this,
                            "Please enter a valid account name.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //BANK VALIDATION
                if (attemptBankName.equals("")) {
                    Toast.makeText(clientreg_bank_info.this,
                            "Please enter a valid bank name.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else {
                    bankName = attemptBankName;
                }

                //ACCOUNT NUM VALIDATION
                String[] accountNumbers = attemptAccountNum.split("");
                if (isAllNumbers(accountNumbers) && accountNumbers.length == 10) {
                    accountNum = attemptAccountNum;
                } else {
                    Toast.makeText(clientreg_bank_info.this,
                            "Please enter a valid account number.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //BSB VALIDATION
                String[] bsbNums = attemptBsb.split("");
                if (isAllNumbers(bsbNums) && bsbNums.length == 7) {
                    bsb = attemptBsb;
                } else {
                    Toast.makeText(clientreg_bank_info.this,
                            "Please enter a valid BSB number.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                if (allValidFields) {
                    progressBar3.setVisibility(View.VISIBLE);
                    // add user to database
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        //check if task is successful
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Client client = new Client(firstName, lastName, email, phone, licenseNum, paymentType, paymentMethod, null, null);

                                FirebaseDatabase.getInstance().getReference("Clients")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //add car to database
                                            addNewCarToDatabase(regNum, transmission, make, model, year, series, engine, email);
                                            //add bank to database
                                            addNewBankToDatabase(accountName, bankName, bsb, accountNum, email);

                                            Toast.makeText(clientreg_bank_info.this, "Registration complete.", Toast.LENGTH_LONG).show();
                                            //verification
                                            sendEmailVerification();

                                            endRegistration();
                                        } else {
                                            Toast.makeText(clientreg_bank_info.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        btnBack7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPaymentMethods();
            }
        });
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

    //Car(licenseNum, regNum, transmission, make, model, year, series, engine, email);
    private void addNewCarToDatabase(String regNum, CarTransmission transmission, CarMake make, CarModel model, int year, String series, String engine, String ownerEmail) {
        String id = CarDatabase.push().getKey();
        Car car = new Car(regNum, transmission, make, model, year, series, engine, email);
        CarDatabase.child(id).setValue(car);

    }

    //PaymentBank(String acctName, String bankName, String bsb, String acctNum, String ownerEmail)
    private void addNewBankToDatabase(String acctName, String bankName, String bsb, String acctNum, String ownerEmail) {
        String id = BankDatabase.push().getKey();
        PaymentBank bank = new PaymentBank(acctName, bankName, bsb, acctNum, email);
        BankDatabase.child(id).setValue(bank);
    }


    public void endRegistration() {
        Intent intent = new Intent(this, clientreg_end.class);
        startActivity(intent);
    }

    public void backToPaymentMethods() {
        Intent intent = new Intent(this, clientreg_pay_method.class);
        startActivity(intent);
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
}
