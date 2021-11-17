package com.begers.tariflerim.service.local.concoretes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.service.local.abstracts.ImageDao;

@Database(entities = {Image.class}, version = 1, exportSchema = false)
public abstract class ImageDatabase extends RoomDatabase {

    private static ImageDatabase instance;

    public abstract ImageDao imageDao();

    public static synchronized ImageDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, ImageDatabase.class, "Image").build();
        }
        return instance;
    }
}
