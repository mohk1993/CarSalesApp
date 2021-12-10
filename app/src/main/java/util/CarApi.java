package util;

import android.app.Application;

import com.example.carsalesapp.model.CarEntity;

public class CarApi extends Application {
    private String userName;
    private static CarApi instance;

     public static CarApi getInstance(){
         if (instance == null)
             instance = new CarApi();
         return instance;
     }
    public CarApi() {}
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
