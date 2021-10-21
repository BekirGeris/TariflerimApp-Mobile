package com.begers.tariflerim.roomdb.concoretes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.roomdb.abstracts.ImageDao;

@Database(entities = {Image.class}, version = 1)
public abstract class ImageDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();
}
