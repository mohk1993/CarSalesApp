package com.example.carsalesapp.converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converters {

    @TypeConverter
    public static byte[] BitMapToByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        if(b==null)
        {
            return null;
        }
        else
            return b;
    }

    @TypeConverter
    public static Bitmap ByteToBitMap(byte[] array) {
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
            if (bitmap == null) {
                return null;
            } else {
                return bitmap;
            }

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
