package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.service.http.abstracts.ImageAPI;
import com.begers.tariflerim.utiles.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageService {

    Gson gson = new GsonBuilder().setLenient().create();

    private ImageAPI api = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ImageAPI.class);

    public Observable<DataResult<List<Image>>> getAll(){
        return api.getAll();
    }

    public Observable<DataResult<Image>> getImageWithUserId(int userId){
        return api.getImageWithUserId(userId);
    }

    public Observable<Result> add(Image image){
        return  api.add(image);
    }

    public Observable<Result> delete(int id){
        return api.delete(id);
    }

}
