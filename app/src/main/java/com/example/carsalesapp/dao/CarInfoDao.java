package com.example.carsalesapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.model.CarInformation;

import java.util.List;

@Dao
public interface CarInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CarEntity carInfo);

    @Query("Delete FROM cars2")
    void deleteAll();

    @Query("SELECT * FROM cars2")
    LiveData<List<CarEntity>> getAllCars();

    @Query("SELECT * FROM cars2 WHERE cars2.id == :id")
    LiveData<CarEntity> get(int id);

    @Update
    void update(CarEntity carEntity);

    @Delete
    void delete(CarEntity carEntity);
}