package com.begers.tariflerim.model.roomdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "Tarifler")
public class TarifRoom {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("userId")
    @ColumnInfo(name = "userId")
    private int userId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("tarif")
    @ColumnInfo(name = "tarif")
    private String tarif;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private Date date;

    @SerializedName("image")
    @ColumnInfo(name = "image")
    private byte[] image;

    public TarifRoom(String name, int userId, String tarif, byte[] image) {
        this.name = name;
        this.userId = userId;
        this.tarif = tarif;
        this.image = image;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public byte[] getImage() {
        return image;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
