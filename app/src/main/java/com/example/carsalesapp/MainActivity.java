package com.example.carsalesapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.viewmodel.UserInformationViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private UserInformationViewModel userInformationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);
        login = findViewById(R.id.loginButton);

         userInformationViewModel = new ViewModelProvider
                .AndroidViewModelFactory(MainActivity.this
                .getApplication()).create(UserInformationViewModel.class);
        login.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(email.getText()) && !TextUtils.isEmpty(password.getText()))
            {
                UserInformation userInformation = new UserInformation
                        (email.getText().toString(),password.getText().toString());
                UserInformationViewModel.insert(userInformation);
            }else {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }
        });


//        UserInformationViewModel userInformationViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
//                .getApplication()).create(UserInformationViewModel.class);
//        userInformationViewModel.getAllUsers().observe(this, userInformation -> {
//            Log.d("Tag", "onCreate: " + userInformation.get(0).getEmail());
//        });
    }
}