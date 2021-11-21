package com.begers.tariflerim.model.api;

import com.google.gson.annotations.SerializedName;

public class Tarif {

    @SerializedName("id")
    private int id;

    @SerializedName("userId")
    private int userId;

    @SerializedName("name")
    private String name;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("date")
    private String date;

    @SerializedName("image")
    private String imageURL;

    public Tarif(String name, int userId, String date, String tarif, String imageURL) {
        this.name = name;
        this.userId = userId;
        this.date = date;
        this.tarif = tarif;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
