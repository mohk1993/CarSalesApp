package com.example.carsalesapp.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "users")
public class UserInformation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    public UserInformation(@NonNull String email, @NonNull String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {return this.email;}

    public String getPassword() {return this.password;}
}
