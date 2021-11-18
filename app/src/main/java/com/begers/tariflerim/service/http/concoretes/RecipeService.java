package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.api.TarifR;
import com.begers.tariflerim.model.dtos.RecipeDto;
import com.begers.tariflerim.service.http.abstracts.RecipeAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeService {
    //https://raw.githubusercontent.com/BekirGeris/depo/main/response_1636466015285.json?token=ALR5PAB2DAYZB62FZB4WRI3BSTTE4
    //https://raw.githubusercontent.com/BekirGeris/depo/main/response_1636466015285.json
    //http://localhost:9999/api/recipes/getAll
    private final String  BASE_URL = "http://10.0.2.2:9999/";

    Gson gson = new GsonBuilder().setLenient().create();

    private RecipeAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RecipeAPI.class);

    public Observable<RecipeDto> getAll(){
        return api.getAll();
    }

    public Completable add(TarifR tarifR){
        return api.add(tarifR);
    }
}

