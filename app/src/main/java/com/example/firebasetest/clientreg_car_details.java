package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.firebasetest.base_registration.email;

// car detail registration
public class clientreg_car_details extends AppCompatActivity {
    EditText fieldRegNum;
    EditText fieldLicenseNum;
    TextView lblTransmission;
    RadioGroup transmissionGroup;
    RadioButton rdBtnAutomatic;
    RadioButton rdBtnManual;
    Spinner spnerMake;
    Spinner spnerModel;
    EditText fieldYear;
    EditText fieldSeries;
    EditText fieldEngine;

    Button btnContinueRegisterClient2;
    Button btnBack3;

    static String licenseNum, regNum;
    static CarTransmission transmission;
    static CarMake make;
    static CarModel model;
    static int year;
    static String series, engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientreg_car_details);

        fieldRegNum = findViewById(R.id.fieldRegNum);
        fieldLicenseNum = findViewById(R.id.fieldLicenseNum);
        lblTransmission = findViewById(R.id.lblTransmission);
        transmissionGroup = findViewById(R.id.transmissionGroup);
        rdBtnAutomatic = findViewById(R.id.rdBtnAutomatic);
        rdBtnManual = findViewById(R.id.rdBtnManual);
        spnerMake = findViewById(R.id.spnerMake);
        spnerMake.setAdapter(new ArrayAdapter<CarMake>(this, android.R.layout.simple_spinner_item, CarMake.values()));
        spnerModel = findViewById(R.id.spnerModel);
        spnerModel.setAdapter(new ArrayAdapter<CarModel>(this, android.R.layout.simple_spinner_item, CarModel.values()));
        fieldYear = findViewById(R.id.fieldYear);
        fieldSeries = findViewById(R.id.fieldSeries);
        fieldEngine = findViewById(R.id.fieldEngine);

        btnContinueRegisterClient2 = findViewById(R.id.btnContinueRegisterClient2);
        btnContinueRegisterClient2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                boolean allValidFields = true;

                String attemptRegNum = fieldRegNum.getText().toString();
                String attemptLicNum = fieldLicenseNum.getText().toString();
                String attemptYear = fieldYear.getText().toString();
                String attemptSeries = fieldSeries.getText().toString();
                String attemptEngine = fieldEngine.getText().toString();

                //LICENSE# VALIDATION
                String[] licenseNums = attemptLicNum.split("");
                if (isAllNumbers(licenseNums) && licenseNums.length == 9) {
                    licenseNum = attemptLicNum;
                } else {
                    Toast.makeText(clientreg_car_details.this,
                            "Please enter a valid license number.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //REG# VALIDATION
                String[] regNums = attemptRegNum.split("");
                if (attemptRegNum.equals("") || regNums.length > 9) { //apparently ACT allows for up to 8 chars on reg#
                    Toast.makeText(clientreg_car_details.this,
                            "Please enter a valid registration number.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else {
                    regNum = attemptRegNum;
                }

                //TRANSMISSION VALIDATION
                if (rdBtnAutomatic.isSelected()) {
                    transmission = CarTransmission.AUTOMATIC;
                } else if (rdBtnManual.isSelected()) {
                    transmission = CarTransmission.MANUAL;
                } else if (transmissionGroup.getCheckedRadioButtonId() == (-1)) {
                    Toast.makeText(clientreg_car_details.this,
                            "Please select a transmission type.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //MAKE VALIDATION
                switch (spnerMake.getSelectedItemPosition()) {
                    case 0:
                        Toast.makeText(clientreg_car_details.this,
                                "Please select a car make.",
                                Toast.LENGTH_LONG).show();
                        allValidFields = false;
                        break;
                    case 1:
                        make = CarMake.MAKE1;
                        break;
                    case 2:
                        make = CarMake.MAKE2;
                        break;
                    case 3:
                        make = CarMake.MAKE3;
                }

                //MODEL VALIDATION
                switch (spnerModel.getSelectedItemPosition()) {
                    case 0:
                        Toast.makeText(clientreg_car_details.this,
                                "Please select a car model.",
                                Toast.LENGTH_LONG).show();
                        allValidFields = false;
                    case 1:
                        model = CarModel.MODEL1;
                        break;
                    case 2:
                        model = CarModel.MODEL2;
                        break;
                    case 3:
                        model = CarModel.MODEL3;
                }

                //YEAR VALIDATION
                String[] yearNums = attemptYear.split("");
                if (isAllNumbers(yearNums) && yearNums.length == 5) {
                    year = Integer.parseInt(attemptYear);
                } else if (attemptYear.equals("") || yearNums.length > 5 || !isAllNumbers(yearNums)) {
                    Toast.makeText(clientreg_car_details.this,
                            "Please enter a valid year. " + yearNums.length,
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                }

                //SERIES VALIDATION
                if (attemptSeries.equals("")) {
                    Toast.makeText(clientreg_car_details.this,
                            "Please enter a valid car series.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else {
                    series = attemptSeries;
                }

                //ENGINE VALIDATION
                if (attemptEngine.equals("")) {
                    Toast.makeText(clientreg_car_details.this,
                            "Please enter a valid car engine.",
                            Toast.LENGTH_LONG).show();
                    allValidFields = false;
                } else {
                    engine = attemptEngine;
                }

                if (allValidFields) {
                    openClientReg2();
                }
            }
        });

        btnBack3 = findViewById(R.id.btnBack3);
        btnBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToBase();
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

    public void openClientReg2() {
        Intent intent = new Intent(this, clientreg_pay_type.class);
        startActivity(intent);
    }

    public void backToBase() {
        Intent intent = new Intent(this, base_registration.class);
        startActivity(intent);
    }
}
