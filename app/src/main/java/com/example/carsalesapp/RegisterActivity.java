package com.example.carsalesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.UserInformationViewModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private UserInformationViewModel userInformationViewModel;
    Button toLogin;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = findViewById(R.id.registerBtn);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confPasswordInput);
        toLogin = findViewById(R.id.toLoginBtn);

        userInformationViewModel = new ViewModelProvider.AndroidViewModelFactory(RegisterActivity.this
                .getApplication()).create(UserInformationViewModel.class);

        toLogin.setOnClickListener(view -> {Intent toLogin = new Intent(RegisterActivity.this,
            LoginActivity.class);
            startActivity(toLogin);
        });

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confPassword = confirmPasswordInput.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confPassword))
            {
                userInformationViewModel.registerUser(email).observe(this, userInformation -> {
                    if ((password.equals(confPassword)) && userInformation == null )
                    {
                        UserInformation userInformation1 = new UserInformation(email,password);
                        UserInformationViewModel.insert(userInformation1);
                        Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(toLogin);
                    }else {
                        Toast.makeText(this,R.string.passNotMatch,Toast.LENGTH_SHORT).show();
                    }
                });
            }else
            {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }

        });
    }
}