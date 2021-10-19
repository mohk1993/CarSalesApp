package com.example.carsalesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.UserInformationViewModel;

public class LoginWindow extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    Button toRegister;
    Button loginButton;

    private UserInformationViewModel userInformationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        toRegister = findViewById(R.id.toRegisterBtn);
        loginButton = findViewById(R.id.loginBtn);
        toRegister.setOnClickListener(v -> {
            Intent toRegister = new Intent(LoginWindow.this, RegisterWindow.class);
            startActivity(toRegister);
        });

        userInformationViewModel = new ViewModelProvider
                .AndroidViewModelFactory(LoginWindow.this
                .getApplication()).create(UserInformationViewModel.class);

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            UserInformation userInformation = new UserInformation(email,password);
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
            {
                if (userInformationViewModel.getUser(email)!=null){
                    Intent logSuccess = new Intent(LoginWindow.this, RegisterWindow.class);
                    startActivity(logSuccess);
                }else {
                    Toast.makeText(this,R.string.failLogin,Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }

        });
    }
}