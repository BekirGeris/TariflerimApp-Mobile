package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageAPI {

    @GET("api/imageControllers/getAll")
    Observable<DataResult<List<Image>>> getAll();

    @GET("api/imageControllers/getImageWithUserId")
    Observable<DataResult<Image>> getImageWithUserId(@Query("userId") int userId);

    @POST("api/imageControllers/add")
    Observable<Result> add(@Body Image image);

    @POST("api/imageControllers/delete")
    Observable<Result> delete(@Query("id") int id);
}
