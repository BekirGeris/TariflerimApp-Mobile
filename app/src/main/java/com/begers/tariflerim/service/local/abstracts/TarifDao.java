package com.begers.tariflerim.service.local.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.begers.tariflerim.model.roomdb.TarifRoom;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TarifDao {

    @Query("SELECT * FROM Tarifler")
    Flowable<List<TarifRoom>> getAll();

    @Query("SELECT * FROM Tarifler WHERE Tarifler.userId = :userId")
    Flowable<List<TarifRoom>> getTarifsUserId(int userId);

    @Insert
    Completable insert(TarifRoom tarifRoom);

    @Update
    Completable update(TarifRoom tarifRoom);

    @Delete
    Completable delete(TarifRoom tarifRoom);
}
