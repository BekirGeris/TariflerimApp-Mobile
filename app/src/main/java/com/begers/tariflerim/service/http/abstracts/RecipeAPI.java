package com.begers.tariflerim.service.http.abstracts;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.model.dtos.RecipeDto;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RecipeAPI {

    //https://raw.githubusercontent.com/BekirGeris/depo/main/response_1636466015285.json?token=ALR5PAGOHRHJ37APHQXL3QLBSUDE2
    @GET("api/recipes/getAll")
    Observable<RecipeDto> getAll();
}
