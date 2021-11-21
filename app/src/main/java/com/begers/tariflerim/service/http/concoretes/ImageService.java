package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.dtos.ImageDto;
import com.begers.tariflerim.model.dtos.ImageListDto;
import com.begers.tariflerim.service.http.abstracts.ImageAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageService {

    private final String  BASE_URL = "http://10.0.2.2:9999/";

    Gson gson = new GsonBuilder().setLenient().create();

    private ImageAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ImageAPI.class);

    public Observable<ImageListDto> getAll(){
        return api.getAll();
    }

    public Observable<ImageDto> getImageWithUserId(int userId){
        return api.getImageWithUserId(userId);
    }

    public Completable add(Image image){
        return  api.add(image);
    }

    public Completable delete(int id){
        return api.delete(id);
    }

}
