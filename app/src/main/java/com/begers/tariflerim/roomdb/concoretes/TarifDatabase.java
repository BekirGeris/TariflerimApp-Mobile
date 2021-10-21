package com.begers.tariflerim.roomdb.concoretes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;

@Database(entities = {Tarif.class}, version = 1)
public abstract class TarifDatabase extends RoomDatabase{
    public abstract TarifDao tarifDao();
}
