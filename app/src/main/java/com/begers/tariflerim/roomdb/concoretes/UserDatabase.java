package com.begers.tariflerim.roomdb.concoretes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
