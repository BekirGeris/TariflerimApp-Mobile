package com.begers.tariflerim.roomdb.concoretes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;

@Database(entities = {Tarif.class}, version = 1)
public abstract class TarifDatabase extends RoomDatabase{

    private static TarifDatabase instance;

    public abstract TarifDao tarifDao();

    public static synchronized TarifDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, TarifDatabase.class, "Tarifler").build();
        }
        return instance;
    }
}
