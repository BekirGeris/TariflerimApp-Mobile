package com.begers.tariflerim.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Tarif;

@Database(entities = {Tarif.class}, version = 1)
public abstract class TarifDatabase extends RoomDatabase{
    public abstract TarifDao tarifDao();
}
