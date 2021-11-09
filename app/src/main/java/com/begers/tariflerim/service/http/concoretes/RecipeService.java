package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.dtos.RecipeDto;
import com.begers.tariflerim.service.http.abstracts.RecipeAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeService {

    //https://raw.githubusercontent.com/BekirGeris/depo/main/response_1636466015285.json
    //http://localhost:9999/api/recipes/getAll
    private final String  BASE_URL = "http://localhost:9999/";

    private RecipeAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RecipeAPI.class);

    public Single<List<Tarif>> getAll(){
        return api.getAll();
    }
}
