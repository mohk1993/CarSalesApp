package com.example.carsalesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carsalesapp.converters.Converters;
import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.viewmodel.CarViewModel;

import util.CarApi;

public class UpdateCarInfoActivity extends AppCompatActivity {
    public static final int GALLERY_CODE = 0;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText modelInput;
    private EditText descInput;
    private EditText manufactureInput;
    private TextView priceTx;
    private Button updateCarBtn;
    private Button deleteCarBtn;
    private TextView userNameIdInUpdate;
    private int carId = 0;
    private Boolean isEdit = false;
    private CarViewModel carViewModel;
    private ImageView updateCarImageId;
    private Button UpdateImageBtnId;
    private Button update_take_image_btn_id;
    Bitmap imageBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car_info);

        userNameIdInUpdate = findViewById(R.id.userNameIdInUpdate);
        modelInput = findViewById(R.id.modelInput);
        descInput = findViewById(R.id.descInput);
        manufactureInput = findViewById(R.id.manufactureInput);
        priceTx = findViewById(R.id.priceInput);
        updateCarBtn = findViewById(R.id.updateCarBtn);
        deleteCarBtn = findViewById(R.id.deleteCarBtn);
        updateCarImageId = findViewById(R.id.updateCarImageId);
        UpdateImageBtnId = findViewById(R.id.UpdateImageBtnId);
        update_take_image_btn_id = findViewById(R.id.update_take_image_btn_id);
        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(UpdateCarInfoActivity.this.getApplication())
                .create(CarViewModel.class);

        if(getIntent().hasExtra(MainActivity.CAR_ID)){
            carId = getIntent().getIntExtra(MainActivity.CAR_ID,0);
            carViewModel.get(carId).observe(this, carEntity -> {
                if (carEntity!=null){
                    userNameIdInUpdate.setText(CarApi.getInstance().getUserName());
                    modelInput.setText(carEntity.getModel());
                    descInput.setText(carEntity.getDescription());
                    manufactureInput.setText(carEntity.getManufacturer());
                    priceTx.setText(carEntity.getPrice().toString());
                    updateCarImageId.setImageBitmap(Converters.ByteToBitMap(carEntity.getImage()));
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

        UpdateImageBtnId.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_CODE);
        });

        update_take_image_btn_id.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(UpdateCarInfoActivity .this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(UpdateCarInfoActivity.this,new String[]{
                        Manifest.permission.CAMERA
                },REQUEST_IMAGE_CAPTURE);
            }
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK){
//            if (data != null){
//                imageUri = data.getData();
//                carImage.setImageURI(imageUri);
//            }
//        }

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    updateCarImageId.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    updateCarImageId.setImageBitmap(imageBitmap);
                }
                break;
        }
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
            carEntity.setFK(CarApi.getInstance().getUserName());
            carEntity.setImage(Converters.BitMapToByte(imageBitmap));
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