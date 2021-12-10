package com.example.carsalesapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carsalesapp.model.UserInformation;

import java.util.List;

@Dao
public interface UserInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserInformation user);

    // might have to remove LiveData later.
    @Query("SELECT * FROM users WHERE users.email==:email and users.password==:password")
    LiveData<UserInformation> getUser(String email,String password);
    @Query("SELECT * FROM users")
    LiveData<List<UserInformation>> getAllUsers();
    @Query("SELECT * FROM users WHERE users.email LIKE :email")
    UserInformation getAccount(String email);
    @Update
    void updateUser(UserInformation user);

    @Delete
    void deleteUser(UserInformation user);
}
