package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.RecipeDto;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeAPI {

    @GET("api/recipes/getAll")
    Observable<RecipeDto> getAll();

    @POST("api/recipes/add")
    Completable add(@Body Tarif tarif);
}
