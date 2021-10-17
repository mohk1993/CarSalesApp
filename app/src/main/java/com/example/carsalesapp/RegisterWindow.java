package com.example.carsalesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterWindow extends AppCompatActivity {

    Button toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);

        toLogin = findViewById(R.id.toLoginBtn);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(RegisterWindow.this, LoginWindow.class);
                startActivity(toRegister);
            }
        });
    }
}