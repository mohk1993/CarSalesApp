package com.example.carsalesapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.carsalesapp.model.CarInformation;

import java.util.List;

@Dao
public interface CarInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CarInformation carInfo);

    @Query("SELECT * FROM cars")
    LiveData<List<CarInformation>> getAllCars();
}
