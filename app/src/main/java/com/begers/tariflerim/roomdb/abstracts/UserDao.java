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

    @Query("SELECT * FROM User WHERE User.id = :id")
    Flowable<User> getUserId(int id);

    @Query("SELECT * FROM User WHERE User.email = :email AND User.password = :password")
    Flowable<User> getUserEmailAndPassword(String email, String password);

    @Query("SELECT Case when count(*) < 1 then 0 else 1 end as bool_value From User Where User.email = :email AND User.password = :password")
    Flowable<Boolean> getBoolEmailAndPassword(String email, String password);

    @Insert
    Completable insert(User user);

    @Delete
    Completable delete(User user);
}
