package com.amati.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class otpPage extends AppCompatActivity {
    ImageView btnBack;
    TextView txtPhone, txtResend;
    Button btnOTP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);

        btnBack = findViewById(R.id.btnBack);
        txtPhone = findViewById(R.id.txt2);
        txtResend = findViewById(R.id.txtResend);
        btnOTP = findViewById(R.id.btnVerify);

        String phN = getIntent().getStringExtra("phone");
        txtPhone.setText("OTP sent to " + phN);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(otpPage.this, "OTP Resent...", Toast.LENGTH_SHORT).show();
            }
        });

        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(otpPage.this, introScreen.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}