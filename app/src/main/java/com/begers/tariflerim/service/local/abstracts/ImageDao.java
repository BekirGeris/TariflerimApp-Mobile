package com.begers.tariflerim.service.local.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.begers.tariflerim.model.Image;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM Image")
    Flowable<List<Image>> getAll();

    @Query("SELECT * FROM Image WHERE Image.userId = :userId")
    Flowable<Image> getImageUserId(int userId);

    @Insert
    Completable insert(Image image);

    @Delete
    Completable delete(Image image);
}
