package com.example.carsalesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carsalesapp.dao.UserInformationDao;
import com.example.carsalesapp.database.UserInformationDb;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.repository.UserInformationRepository;
import com.example.carsalesapp.viewmodel.UserInformationViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_EMAIL = "user_email";
    private EditText emailInput;
    private EditText passwordInput;
    Button toRegister;
    Button loginButton;
    private UserInformationViewModel userInformationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        toRegister = findViewById(R.id.toRegisterBtn);
        loginButton = findViewById(R.id.loginBtn);
        toRegister.setOnClickListener(v -> {
            Intent toRegister = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(toRegister);
        });

        userInformationViewModel = new ViewModelProvider .AndroidViewModelFactory(LoginActivity.this
        .getApplication()).create(UserInformationViewModel.class);
        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
            {
                userInformationViewModel.getUser(email,password).observe(this, userInformation -> {
                    if (userInformation != null){
                        Intent logSuccess = new Intent(LoginActivity.this, MainActivity.class);
                        logSuccess.putExtra(USER_EMAIL,email);
                        startActivity(logSuccess);
                        Toast.makeText(getBaseContext(), "Successfully Logged In!", Toast.LENGTH_LONG).show();
                        Log.i("Successful_Login", "Login was successful");
                    }else {
                        Toast.makeText(this,R.string.failLogin,Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }
        });


    }
}