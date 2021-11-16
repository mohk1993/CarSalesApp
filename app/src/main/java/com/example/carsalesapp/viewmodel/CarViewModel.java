package com.example.carsalesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.carsalesapp.model.CarEntity;
import com.example.carsalesapp.repository.CarRepository;

import java.util.List;

public class CarViewModel extends AndroidViewModel {
    public static CarRepository repository;
    public final LiveData<List<CarEntity>> allCars;
    public CarViewModel(@NonNull Application application) {
        super(application);

        repository = new CarRepository(application);
        allCars = repository.getAllCars();
    }

    public LiveData<List<CarEntity>> getCars(){
        return allCars;
    }
    public static void insert(CarEntity carEntity){repository.insert(carEntity);}
    public LiveData<CarEntity> get(int id) {return repository.get(id);}
    public static void update(CarEntity carEntity) {repository.update(carEntity);}
    public static void delete(CarEntity carEntity) {repository.delete(carEntity);}
}
