package com.example.carsalesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.carsalesapp.model.CarInformation;
import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.repository.CarInformationRepository;
import com.example.carsalesapp.repository.UserInformationRepository;

import java.util.List;

public class UserInformationViewModel extends AndroidViewModel {

    public static UserInformationRepository repository;
    public final LiveData<List<UserInformation>> allUsers;

    public UserInformationViewModel (@NonNull Application application)
    {
        super(application);
        repository = new UserInformationRepository(application);
        allUsers = repository.getAllData();
    }

    public LiveData<List<UserInformation>> getAllUsers() {return allUsers;}
    public static void insert(UserInformation userInformation) {repository.insertUser(userInformation);}

    public LiveData<UserInformation> getUser(String email) {return repository.getUser(email);}

    public static void updateUser(UserInformation userInformation){repository.updateUser(userInformation);}

    public static void delete(UserInformation userInformation){repository.deleteUser(userInformation);}
}
