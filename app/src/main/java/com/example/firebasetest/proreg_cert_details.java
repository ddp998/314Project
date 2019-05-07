package com.example.firebasetest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.firebasetest.base_registration.email;

public class proreg_cert_details extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText fieldCertTitle;
    EditText fieldIssuedBy;
    EditText fieldIssueDate;

    Button btnContinueProReg;
    Button btnBackToProDetails;

    static ArrayList<Certification> certs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proreg_cert_details);

        fieldCertTitle = findViewById(R.id.fieldCertTitle);
        fieldIssuedBy = findViewById(R.id.fieldIssuedBy);

        // date field
        fieldIssueDate = findViewById(R.id.fieldIssueDate);
        fieldIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        //CONTINUE BUTTON
        btnContinueProReg = findViewById(R.id.btnContinueProReg);
        btnContinueProReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fieldCertTitle.getText().toString().equals("") && !fieldIssuedBy.getText().toString().equals("") && !fieldIssueDate.getText().toString().equals("")) {
                    Certification c = new Certification(fieldCertTitle.getText().toString(), fieldIssuedBy.getText().toString(), fieldIssueDate.getText().toString(), email);
                    certs.add(c);

                    openCertOverview();
                } else {
                    Toast.makeText(proreg_cert_details.this, "All fields must be filled out.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //BACK BUTTON
        btnBackToProDetails = findViewById(R.id.btnBackToProDetails);
        btnBackToProDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToProDetails();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        fieldIssueDate = findViewById(R.id.fieldIssueDate);
        fieldIssueDate.setText(currentDateString);
    }

    public void openCertOverview() {
        Intent intent = new Intent(this, proreg_cert_overview.class);
        startActivity(intent);
    }

    public void backToProDetails() {
        Intent intent = new Intent(this, proreg_pro_details.class);
        startActivity(intent);
    }
}