package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.model.api.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {

    @GET("api/users/getAll")
    Observable<DataResult<List<User>>> getAll();

    @GET("api/users/getByUserId")
    Observable<DataResult<User>> getUserWithUserId(@Query("userId")  int userId);

    @GET("api/users/getByUserEmailAndPassword")
    Observable<DataResult<User>> getUserWithEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @POST("api/users/add")
    Observable<Result> add(@Body User user);
}
