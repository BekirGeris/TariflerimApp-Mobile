package com.begers.tariflerim.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.begers.tariflerim.model.Tarif;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TarifDao {

    @Query("SELECT * FROM Tarifler")
    Flowable<List<Tarif>> getAll();

    @Insert
    Completable insert(Tarif tarif);

    @Delete
    Completable delete(Tarif tarif);
}
