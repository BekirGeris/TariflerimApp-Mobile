package com.begers.tariflerim.roomdb.concoretes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.roomdb.abstracts.ImageDao;

@Database(entities = {Image.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {

    private static ImageDatabase instance;

    public abstract ImageDao imageDao();

    public static ImageDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, ImageDatabase.class, "Image").build();
        }
        return instance;
    }
}
