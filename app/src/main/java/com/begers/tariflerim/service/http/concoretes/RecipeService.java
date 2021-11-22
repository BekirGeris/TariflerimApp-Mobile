package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.service.http.abstracts.RecipeAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class RecipeService {
    private final String  BASE_URL = "http://192.168.1.104:9999/";

    Gson gson = new GsonBuilder().setLenient().create();

    private RecipeAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RecipeAPI.class);

    public Observable<DataResult<List<Tarif>>> getAll(){
        return api.getAll();
    }

    public Observable<DataResult<List<Tarif>>> getTarifsWithUserId(@Query("userId") int userId){
        return api.getTarifsWithUserId(userId);
    }

    public Observable<Result> add(Tarif tarif){
        return api.add(tarif);
    }
}

