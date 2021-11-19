package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.dtos.UserDto;
import com.begers.tariflerim.model.dtos.UserDto2;
import com.begers.tariflerim.model.roomdb.User;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {

    @GET("api/users/getAll")
    Observable<UserDto> getAll();

    @GET("api/users/getByUserId")
    Observable<UserDto2> getUserWithUserId(@Query("userId")  int userId);

    @GET("api/users/getByUserEmailAndPassword")
    Observable<UserDto2> getUserWithEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @POST("api/users/add")
    Completable add(@Body User user);

}
