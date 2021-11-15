package com.example.carsalesapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cars")
public class CarInformation implements Serializable {

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

    //remake to be Description, @nullable link, price, city, mielage, poer, gearbox, date of manufacture, bodyType;
    public CarInformation(@NonNull String model, @NonNull String manufacturer, String description, @NonNull Double price) {
        this.model = model;
        this.manufacturer = manufacturer;
        this.description = description;
        this.price = price;
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
}
