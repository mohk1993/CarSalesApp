package com.example.carsalesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.viewmodel.CarViewModel;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int GALLERY_CODE = 1;
    EditText modelInput;
    EditText descInput;
    EditText manufactureInput;
    TextView priceTx;
    Button addCarBtn;
    Button selectImageBtn;
    private ImageView carImage;
    private String FK;
    private CarViewModel carViewModel;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        modelInput = findViewById(R.id.modelInput);
        descInput = findViewById(R.id.descInput);
        manufactureInput = findViewById(R.id.manufactureInput);
        priceTx = findViewById(R.id.priceInput);
        carImage = findViewById(R.id.carImage);
        addCarBtn = findViewById(R.id.updateCarBtn);
        selectImageBtn = findViewById(R.id.selectImage);
        selectImageBtn.setOnClickListener(this);
        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(AddCarActivity.this.getApplication())
                .create(CarViewModel.class);
        addCarBtn.setOnClickListener(v -> {
            String model = modelInput.getText().toString().trim();
            String description = descInput.getText().toString().trim();
            String price = priceTx.getText().toString().trim();
            Double priceVal = Double.parseDouble(priceTx.getText().toString());
            String manufacturer = manufactureInput.getText().toString().trim();
            Bundle data = getIntent().getExtras();
            if(data!=null){FK = data.getString(MainActivity.USER_EMAIL);}
            Log.d("FK", "onCreate:FK " + FK);
            if (!TextUtils.isEmpty(model) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(price)
                && !TextUtils.isEmpty(manufacturer))
            {
                    CarEntity carEntity = new CarEntity(model,manufacturer,description,priceVal,FK);
                    CarViewModel.insert(carEntity);
                    Intent carAdded = new Intent(AddCarActivity.this, MainActivity.class);
                    startActivity(carAdded);
            }else
            {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }
        });

        Bundle data = getIntent().getExtras();
        if(data!=null){
            int id = data.getInt(MainActivity.CAR_ID);
            carViewModel.get(id).observe(this, carEntity -> {
                modelInput.setText(carEntity.getModel());
                descInput.setText(carEntity.getDescription());
                manufactureInput.setText(carEntity.getManufacturer());
                priceTx.setText(carEntity.getPrice().toString());
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selectImage:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
                break;
            case R.id.photoCar:
                // take photo from camera
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
            if (data != null){
                imageUri = data.getData();
                carImage.setImageURI(imageUri);
            }
        }
    }
}