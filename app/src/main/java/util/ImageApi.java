package util;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.carsalesapp.model.CarEntity;

public class ImageApi extends Application {
    private Bitmap image;
    private static ImageApi instance;

    public static ImageApi getInstance(){
        if (instance == null)
            instance = new ImageApi();
        return instance;
    }
    public ImageApi() {}

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
