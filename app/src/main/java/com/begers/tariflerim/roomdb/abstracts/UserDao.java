package com.begers.tariflerim.roomdb.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.begers.tariflerim.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM User WHERE User.id = id")
    Flowable<User> getUserId(int id);

    @Insert
    Completable insert(User user);

    @Delete
    Completable delete(User user);
}
