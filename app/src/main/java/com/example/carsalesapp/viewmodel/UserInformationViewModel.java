package com.example.carsalesapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.carsalesapp.model.UserInformation;
import com.example.carsalesapp.repository.UserInformationRepository;

public class UserInformationViewModel extends AndroidViewModel {

    private UserInformationRepository repository;

    public UserInformationViewModel (Application application)
    {
        super(application);
        repository = new UserInformationRepository(application.getApplicationContext());
    }

    LiveData<UserInformation> getUser(String email) {return repository.getUser(email);}

}
