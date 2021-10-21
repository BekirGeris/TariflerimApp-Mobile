package com.begers.tariflerim.roomdb.concoretes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "User").build();
        }
        return instance;
    }
}
