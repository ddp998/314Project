package com.example.firebasetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import static com.example.firebasetest.clientreg_car_details.transmission;
import static com.example.firebasetest.clientreg_car_details.year;
import static com.example.firebasetest.clientreg_pay_type.paymentType;

public class clientreg_pay_method extends AppCompatActivity {
    Button btnCardMethod, btnBankMethod, btnSkip, btnBack5;
    ProgressBar paymentMethodProgressBar;

    static PaymentMethod paymentMethod;

    FirebaseAuth firebaseAuth;
    DatabaseReference CarDatabase;

    private static final String TAG = "clientreg_pay_method";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_pay_method);

        // [START initialize_database_ref]
        CarDatabase = FirebaseDatabase.getInstance().getReference("Cars");
        // [END initialize_database_ref]

        btnCardMethod = findViewById(R.id.btnCardMethod);
        btnBankMethod = findViewById(R.id.btnBankMethod);
        btnSkip = findViewById(R.id.btnSkip);
        btnBack5 = findViewById(R.id.btnBack5);
        paymentMethodProgressBar = findViewById(R.id.paymentMethodProgressBar);

        btnCardMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                paymentMethod = PaymentMethod.CARD;
                openCardInfo();
            }
        });

        btnBankMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                paymentMethod = PaymentMethod.BANK;
                openBankInfo();
            }
        });

        //create Firebase instance
        firebaseAuth = FirebaseAuth.getInstance();
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMethod = PaymentMethod.UNSAVED;

                //show progress bar
                paymentMethodProgressBar.setVisibility(View.VISIBLE);

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

                                        Toast.makeText(clientreg_pay_method.this, "Registration complete.", Toast.LENGTH_LONG).show();
                                        //verification
                                        sendEmailVerification();

                                        endRegistration();
                                    } else {
                                        Toast.makeText(clientreg_pay_method.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        // end firebase auth

        btnBack5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                backToPayType();
            }
        });
    }

    public void openCardInfo() {
        Intent intent = new Intent(this, clientreg_card_info.class);
        startActivity(intent);
    }

    public void openBankInfo() {
        Intent intent = new Intent(this, clientreg_bank_info.class);
        startActivity(intent);
    }

    public void endRegistration() {
        Intent intent = new Intent(this, clientreg_end.class);
        startActivity(intent);
    }

    public void backToPayType() {
        Intent intent = new Intent(this, clientreg_pay_type.class);
        startActivity(intent);
    }

    //Car(licenseNum, regNum, transmission, make, model, year, series, engine, email);
    private void addNewCarToDatabase(String regNum, CarTransmission transmission, CarMake make, CarModel model, int year, String series, String engine, String ownerEmail) {
        String id = CarDatabase.push().getKey();
        Car car = new Car(regNum, transmission, make, model, year, series, engine, email);
        CarDatabase.child(id).setValue(car);

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
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
        // [END send_email_verification]
    }
}
