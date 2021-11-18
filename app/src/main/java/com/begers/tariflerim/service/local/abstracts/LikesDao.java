package com.begers.tariflerim.service.local.abstracts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.begers.tariflerim.model.roomdb.Likes;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface LikesDao {

    @Query("SELECT * FROM Likes WHERE Likes.tarifId = :postId")
    Flowable<List<Likes>> getAllLikesOfPost(int postId);

    @Insert
    Completable insert(Likes likes);

    @Update
    Completable update(Likes likes);

    @Delete
    Completable delete(Likes likes);
}
