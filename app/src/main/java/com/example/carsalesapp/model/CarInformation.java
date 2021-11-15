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
    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "imageLink")
    private String imageLink;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private Double price;

    public CarInformation(@NonNull String description, String city, @NonNull Double price, String link, String imageLink) {
        this.description = description;
        this.city = city;
        this.price = price;

        this.link = link;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getLink() { return link == null ? "" : link; }

    public void setLink(String link) { this.link = link; }

    public String getImageLink() { return imageLink == null ? "" : imageLink; }

    public void setImageLink(String imageLink) { this.imageLink = imageLink; }
}
