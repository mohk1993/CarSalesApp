package com.example.carsalesapp.repository;
import android.app.Application;
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
    private UserInformationDao userInformationDao;
    private LiveData<List<UserInformation>> allUsers;

    public UserInformationRepository(Application application) {
        UserInformationDb db = UserInformationDb.getDatabase(application);
        userInformationDao = db.userDaoAccess();

        allUsers = userInformationDao.getAllUsers();
    }

    public LiveData<List<UserInformation>> getAllData(){return allUsers;}
        public void insertUser(UserInformation user)
    {
        // This is important in order to run insert on the main thread
        UserInformationDb.databaseWriteExecutor.execute(()->{
            userInformationDao.insert(user);
        });
    }
    public boolean isValidAccount(String email, final String password)
    {

        UserInformation userInformation = userInformationDao.getAccount(email);
        return userInformation.getPassword().equals(password);
    }
    public LiveData <UserInformation> getUser(String email, String password){
       return userInformationDao.getUser(email, password); }
    public LiveData <UserInformation> registerUser(String email){
        return userInformationDao.registerUser(email); }
    public void updateUser(UserInformation userInformation) {
        UserInformationDb.databaseWriteExecutor.execute(()->userInformationDao.updateUser(userInformation));
    }

    public void deleteUser(UserInformation userInformation){
        UserInformationDb.databaseWriteExecutor.execute(()->userInformationDao.deleteUser(userInformation));
    }
}


/* ==================================== Old Info ===================================
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

 */