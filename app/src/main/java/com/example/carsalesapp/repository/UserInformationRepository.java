package com.example.carsalesapp.repository;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.carsalesapp.dao.UserInformationDao;
import com.example.carsalesapp.database.UserInformationDb;
import com.example.carsalesapp.model.UserInformation;

import java.util.List;

public class UserInformationRepository {

    private String DB_NAME = "users";

    private UserInformationDb userDb;

    public UserInformationRepository(Context context) {
        userDb = Room.databaseBuilder(context, UserInformationDb.class, DB_NAME).build();
    }

    public void updateUser(UserInformation user)
    {
        userDb.userDaoAccess().updateUser(user);
    }

    public void deleteUser(UserInformation user)
    {
        userDb.userDaoAccess().deleteUser(user);
    }

    public LiveData<UserInformation> getUser(String email){
        return userDb.userDaoAccess().getUser((email));
    }

    public LiveData<List<UserInformation>> getAllUsers() {
        return userDb.userDaoAccess().getAllUsers();
    }

    public void insertUser(UserInformation user)
    {
        userDb.userDaoAccess().insert(user);
    }
}
