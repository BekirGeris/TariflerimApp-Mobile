package com.begers.tariflerim.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Blob;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

@Entity(tableName = "Tarifler")
public class Tarif {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "image")
    private byte[] image;

    public Tarif(String name,int userId, String tarif, byte[] image) {
        this.name = name;
        this.userId = userId;
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

    public void setImage(byte[] image) {
        this.image = image;
    }
}
