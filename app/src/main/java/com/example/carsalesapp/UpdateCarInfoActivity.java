package com.example.carsalesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.viewmodel.CarViewModel;

public class UpdateCarInfoActivity extends AppCompatActivity {
    EditText modelInput;
    EditText descInput;
    EditText manufactureInput;
    TextView priceTx;
    Button updateCarBtn;
    Button deleteCarBtn;
    private String FK;
    private int carId = 0;
    private Boolean isEdit = false;
    private CarViewModel carViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_info);

        modelInput = findViewById(R.id.modelInput);
        descInput = findViewById(R.id.descInput);
        manufactureInput = findViewById(R.id.manufactureInput);
        priceTx = findViewById(R.id.priceInput);
        updateCarBtn = findViewById(R.id.updateCarBtn);
        deleteCarBtn = findViewById(R.id.deleteCarBtn);

        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(UpdateCarInfoActivity.this.getApplication())
                .create(CarViewModel.class);

        if(getIntent().hasExtra(MainActivity.CAR_ID)){
            carId = getIntent().getIntExtra(MainActivity.CAR_ID,0);
            carViewModel.get(carId).observe(this, carEntity -> {
                if (carEntity!=null){
                    modelInput.setText(carEntity.getModel());
                    descInput.setText(carEntity.getDescription());
                    manufactureInput.setText(carEntity.getManufacturer());
                    priceTx.setText(carEntity.getPrice().toString());
                }
            });
            isEdit = true;
        }
        deleteCarBtn.setOnClickListener(view -> {
            edit(true);
        });
        updateCarBtn.setOnClickListener(view -> {
            edit(false);
        });

    }

    private void edit(Boolean isDelete) {
        int id = carId;
        String model = modelInput.getText().toString().trim();
        String description = descInput.getText().toString().trim();
        String price = priceTx.getText().toString().trim();
        Double priceVal = Double.parseDouble(priceTx.getText().toString());
        String manufacturer = manufactureInput.getText().toString().trim();
        if (!TextUtils.isEmpty(model) && !TextUtils.isEmpty(manufacturer) && !TextUtils.isEmpty(price))
        {
            CarEntity carEntity = new CarEntity();
            carEntity.setId(id);
            carEntity.setModel(model);
            carEntity.setDescription(description);
            carEntity.setPrice(priceVal);
            carEntity.setManufacturer(manufacturer);
            carEntity.setFK(FK);
            if (isDelete)
                CarViewModel.delete(carEntity);
            else
                CarViewModel.update(carEntity);
            finish();
        }else
        {
            Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
        }
    }
}