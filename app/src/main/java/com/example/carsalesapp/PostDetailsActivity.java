package com.example.carsalesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carsalesapp.adapter.RecyclerViewAdapter;
import com.example.carsalesapp.converters.Converters;
import com.example.carsalesapp.viewmodel.CarViewModel;

import util.CarApi;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView modelInfo;
    private TextView descriptionInfo;
    private TextView manufactureInfo;
    private TextView priceInfo;
    private ImageView CarImageInfo;
    private TextView userNameInfo;
    private CarViewModel carViewModel;
    private int carId = 0;
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

        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(PostDetailsActivity.this.getApplication())
                .create(CarViewModel.class);

        if(getIntent().hasExtra(RecyclerViewAdapter.CAR_ID)){
            carId = getIntent().getIntExtra(MainActivity.CAR_ID,0);
            carViewModel.get(carId).observe(this, carEntity -> {
                if (carEntity!=null){
                    userNameInfo.setText(CarApi.getInstance().getUserName());
                    modelInfo.setText(carEntity.getModel());
                    descriptionInfo.setText(carEntity.getDescription());
                    manufactureInfo.setText(carEntity.getManufacturer());
                    priceInfo.setText(carEntity.getPrice().toString());
                    CarImageInfo.setImageBitmap(Converters.ByteToBitMap(carEntity.getImage()));
                }
            });
        }
    }
}