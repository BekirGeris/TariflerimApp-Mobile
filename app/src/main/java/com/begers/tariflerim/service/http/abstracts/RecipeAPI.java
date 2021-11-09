package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.model.dtos.RecipeDto;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeAPI {

    @GET("api/recipes/getAll")
    Single<List<Tarif>> getAll();
}
