package com.example.carsalesapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cars2")
public class CarEntity implements Serializable, Comparable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "manufacturer")
    private String manufacturer;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "FK")
    private String FK;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public CarEntity() {
    }

    public CarEntity(@NonNull String model, @NonNull String manufacturer, String description, @NonNull Double price, @NonNull String FK, byte[] image) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.description = description;
        this.price = price;
        this.FK = FK;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFK() {
        return FK;
    }

    public void setFK(String FK) {
        this.FK = FK;
    }

    @Override
    public int compareTo(Object car) {
        Double compareTo = ((CarEntity)car).getPrice();
        if(this.price - ((CarEntity) car).getPrice() > 0){
            return 1;
        }else if(this.price - ((CarEntity) car).getPrice() < 0){
            return -1;
        }
        return 0;
    }
}
