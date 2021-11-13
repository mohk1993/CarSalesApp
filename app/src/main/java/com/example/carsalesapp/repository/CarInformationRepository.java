package com.example.carsalesapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carsalesapp.dao.CarInformationDao;
import com.example.carsalesapp.database.CarInformationDb;
import com.example.carsalesapp.model.CarInformation;

import java.util.List;

public class CarInformationRepository {

    private CarInformationDao carInformationDao;
    private LiveData<List<CarInformation>> allCars;

    public CarInformationRepository(Application application)
    {
        CarInformationDb db = CarInformationDb.getDatabase(application);
        carInformationDao = db.carDaoAccess();

        allCars = carInformationDao.getAllCars();
    }

    public LiveData<List<CarInformation>> getAllCars()
    {
        return allCars;
    }
}
