package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.dtos.UserListDto;
import com.begers.tariflerim.model.dtos.UserDto;
import com.begers.tariflerim.model.roomdb.User;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @GET("api/users/getAll")
    Observable<UserListDto> getAll();

    @GET("api/users/getByUserId")
    Observable<UserDto> getUserWithUserId(@Query("userId")  int userId);

    @GET("api/users/getByUserEmailAndPassword")
    Observable<UserDto> getUserWithEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @POST("api/users/add")
    Completable add(@Body User user);

}
