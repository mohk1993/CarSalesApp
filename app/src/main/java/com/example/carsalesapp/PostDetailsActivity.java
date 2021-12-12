package com.example.carsalesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView modelInfo;
    private TextView descriptionInfo;
    private TextView manufactureInfo;
    private TextView priceInfo;
    private ImageView CarImageInfo;
    private TextView userNameInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        userNameInfo = findViewById(R.id.userNameInfoId);
        modelInfo = findViewById(R.id.modelInfoId);
        descriptionInfo = findViewById(R.id.descriptionInfoId);
        manufactureInfo = findViewById(R.id.manufactureInfoId);
        priceInfo = findViewById(R.id.priceInfoId);
        CarImageInfo = findViewById(R.id.CarImageInfoId);
    }
}