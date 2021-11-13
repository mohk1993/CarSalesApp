package com.example.carsalesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.repository.CarInformationRepository;

import java.util.List;

public class CarInformationViewModel extends AndroidViewModel {

    public static CarInformationRepository repository;
    public final LiveData<List<CarInformation>> allCars;

    public CarInformationViewModel(@NonNull Application application) {
        super(application);

        repository = new CarInformationRepository(application);
        allCars = repository.getAllCars();
    }

    public LiveData<List<CarInformation>> getCars(){
        return allCars;
    }

}
