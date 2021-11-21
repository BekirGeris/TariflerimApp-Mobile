package com.begers.tariflerim.service.local.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.begers.tariflerim.model.roomdb.ImageRoom;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM ImageRoom")
    Flowable<List<ImageRoom>> getAll();

    @Query("SELECT * FROM ImageRoom WHERE ImageRoom.userId = :userId")
    Flowable<ImageRoom> getImageUserId(int userId);

    @Insert
    Completable insert(ImageRoom imageRoom);

    @Delete
    Completable delete(ImageRoom imageRoom);
}
