package com.begers.tariflerim.service.http.abstracts;

import android.provider.ContactsContract;

import com.begers.tariflerim.model.api.TarifR;
import com.begers.tariflerim.model.dtos.RecipeDto;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeAPI {

    @GET("api/recipes/getAll")
    Observable<RecipeDto> getAll();

    @POST("api/recipes/add")
    Completable add(@Body TarifR tarifR);
}
