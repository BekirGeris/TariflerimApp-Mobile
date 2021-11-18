package com.begers.tariflerim.model.api;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class TarifR {

    @SerializedName("id")
    private int id;

    @SerializedName("userId")
    private int userId;

    @SerializedName("name")
    private String name;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("date")
    public String date;

    @SerializedName("image")
    private String image;

    public TarifR(String name,int userId, String date, String tarif, String image) {
        this.name = name;
        this.userId = userId;
        this.date = date;
        this.tarif = tarif;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
