package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.dtos.ImageDto;
import com.begers.tariflerim.model.dtos.ImageListDto;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageAPI {

    @GET("api/imageControllers/getAll")
    Observable<ImageListDto> getAll();

    @GET("api/imageControllers/getImageWithUserId")
    Observable<ImageDto> getImageWithUserId(@Query("userId") int userId);

    @POST("api/imageControllers/add")
    Completable add(@Body Image image);

    @POST("api/imageControllers/delete")
    Completable delete(@Query("id") int id);
}
