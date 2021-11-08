package com.begers.tariflerim.service.concoretes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.Likes;
import com.begers.tariflerim.service.abstracts.LikesDao;

@Database(entities = {Likes.class}, version = 1)
public abstract class LikesDatabase extends RoomDatabase {

    private static LikesDatabase instance;

    public abstract LikesDao likesDao();

    public static synchronized LikesDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, LikesDatabase.class, "Likes").build();
        }
        return instance;
    }
}
