package com.example.carsalesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.CarApi;

public class AddCarActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int GALLERY_CODE = 0;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText modelInput;
    private EditText descInput;
    private EditText manufactureInput;
    private TextView priceTx;
    private Button addCarBtn;
    private Button selectImageBtnId;
    private ImageView carImage;
    private String CarFK;
    private CarViewModel carViewModel;
    private Uri imageUri;
    private Button TakePhotoBtnId;
    Bitmap imageBitmap;º
    byte[] imageByte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        modelInput = findViewById(R.id.modelInput);
        descInput = findViewById(R.id.descInput);
        manufactureInput = findViewById(R.id.manufactureInput);
        priceTx = findViewById(R.id.priceInput);
        carImage = findViewById(R.id.carImageId);
        addCarBtn = findViewById(R.id.addCarBtnId);
        selectImageBtnId = findViewById(R.id.selectImageBtnId);
        TakePhotoBtnId = findViewById(R.id.TakePhotoBtnId);
        selectImageBtnId.setOnClickListener(this);
        TakePhotoBtnId.setOnClickListener(this);
        carViewModel = new ViewModelProvider
                .AndroidViewModelFactory(AddCarActivity.this.getApplication())
                .create(CarViewModel.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        addCarBtn.setOnClickListener(v -> {
            String model = modelInput.getText().toString().trim();
            String description = descInput.getText().toString().trim();
            String price = priceTx.getText().toString().trim();
            Double priceVal = Double.parseDouble(priceTx.getText().toString());
            String manufacturer = manufactureInput.getText().toString().trim();
            imageByte = Converters.BitMapToByte(imageBitmap);
            Bundle data = getIntent().getExtras();
            if(data!=null){CarFK = data.getString(MainActivity.USER_EMAIL);}
            Log.d("FK", "onCreate:FK " + CarFK);
            if (!TextUtils.isEmpty(model) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(price)
                    && !TextUtils.isEmpty(manufacturer))
            {
                CarEntity carEntity = new CarEntity(model,manufacturer,description,priceVal, CarApi.getInstance().getUserName(),imageByte);
                CarViewModel.insert(carEntity);
                Intent carAdded = new Intent(AddCarActivity.this, MainActivity.class);
                startActivity(carAdded);
            }else
            {
                Toast.makeText(this,R.string.empty,Toast.LENGTH_SHORT).show();
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notification");
            builder.setContentTitle("New Car Added");
            builder.setContentText("Someone added a new car that may interest you.");
            builder.setSmallIcon(R.drawable.logo);
            builder.setAutoCancel(true);

            Intent intent = new Intent(AddCarActivity.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AddCarActivity.this
                    ,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(1,builder.build());
        });


//        Bundle data = getIntent().getExtras();
//        if(data!=null){
//            int id = data.getInt(MainActivity.CAR_ID);
//            CarFK = data.getString(MainActivity.USER_EMAIL);
//            carViewModel.get(id).observe(this, carEntity -> {
//                modelInput.setText(carEntity.getModel());
//                descInput.setText(carEntity.getDescription());
//                manufactureInput.setText(carEntity.getManufacturer());
//                priceTx.setText(carEntity.getPrice().toString());
//            });
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.selectImageBtnId:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
                break;
            case R.id.TakePhotoBtnId:
                // take photo from camera
                if(ContextCompat.checkSelfPermission(AddCarActivity .this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(AddCarActivity.this,new String[]{
                            Manifest.permission.CAMERA
                    },REQUEST_IMAGE_CAPTURE);
                }
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                break;
        }
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
                    carImage.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Bundle extras = data.getExtras();
                    imageBitmap = (Bitmap) extras.get("data");
                    carImage.setImageBitmap(imageBitmap);
                }
                break;
        }
    }
}