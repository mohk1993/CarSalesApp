package com.example.carsalesapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.carsalesapp.dao.CarInfoDao;
import com.example.carsalesapp.database.CarDb;
import com.example.carsalesapp.database.UserInformationDb;
import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.model.UserInformation;

import java.util.List;

public class CarRepository {
    private CarInfoDao carInfoDao;
    private LiveData<List<CarEntity>> allCars;

    public CarRepository(Application application)
    {
        CarDb db = CarDb.getDatabase(application);
        carInfoDao = db.carDaoAccess();

        allCars = carInfoDao.getAllCars();
    }

    public LiveData<List<CarEntity>> getAllCars()
    {
        return allCars;
    }

    public LiveData<List<CarEntity>> getAllCurrentUsrCars(String currentUser)
    {
        return carInfoDao.getAllCurrentUsrCars(currentUser);
    }

    public void insert(CarEntity carEntity)
    {
        // This is important in order to run insert on the main thread
        CarDb.databaseWriteExecutor.execute(()->{
            carInfoDao.insert(carEntity);
        });
    }

    public LiveData<CarEntity> get(int id){
        return carInfoDao.get(id);
    }

    public void update(CarEntity carEntity)
    {
        // This is important in order to run insert on the main thread
        CarDb.databaseWriteExecutor.execute(()->{
            carInfoDao.update(carEntity);
        });
    }

    public void delete(CarEntity carEntity)
    {
        // This is important in order to run insert on the main thread
        CarDb.databaseWriteExecutor.execute(()->{
            carInfoDao.delete(carEntity);
        });
    }
}
