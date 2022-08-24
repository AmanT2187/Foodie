package com.amati.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginPage extends AppCompatActivity {
    TextView txtPolicy;
    Button btnLogin;
    EditText edtPhone;

    private final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        txtPolicy = findViewById(R.id.policy);
        txtPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        edtPhone = findViewById(R.id.et_phone);
        String ph = edtPhone.getText().toString();

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtPhone.equals("")){
                    if(edtPhone.length()==10){
                        Intent i = new Intent(loginPage.this, otpPage.class);
                        i.putExtra("phone", edtPhone.getText().toString());
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "OTP Sent", Toast.LENGTH_SHORT).show();

                        //code to be changed
                        finish();
                    }else{
                        edtPhone.setError("Enter Valid Phone number");
                    }
                }else{
                    edtPhone.setError("Enter phone number");
                }
            }
        });

        //Check permission
        if (ContextCompat.checkSelfPermission(loginPage.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(loginPage.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(loginPage.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else{
                ActivityCompat.requestPermissions(loginPage.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(loginPage.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}