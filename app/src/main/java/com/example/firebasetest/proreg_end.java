package com.example.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class proreg_end extends AppCompatActivity {
    Button btnStartApp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proreg_end);

        btnStartApp2 = findViewById(R.id.btnStartApp2);
        btnStartApp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProDashMap();
            }
        });
    }

    public void openProDashMap() {
        Intent intent = new Intent(this, pro_dashmap.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
