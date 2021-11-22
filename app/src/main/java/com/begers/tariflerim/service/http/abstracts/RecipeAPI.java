package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecipeAPI {

    @GET("api/recipes/getAll")
    Observable<DataResult<List<Tarif>>> getAll();

    @GET("api/recipes/getTarifsWithUserId")
    Observable<DataResult<List<Tarif>>> getTarifsWithUserId(@Query("userId") int userId);

    @POST("api/recipes/add")
    Observable<Result> add(@Body Tarif tarif);
}
