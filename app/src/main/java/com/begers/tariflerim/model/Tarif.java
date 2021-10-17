package com.begers.tariflerim.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tarif {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "tarif")
    private String tarif;

    @ColumnInfo(name = "image")
    private Byte[] image;
}
