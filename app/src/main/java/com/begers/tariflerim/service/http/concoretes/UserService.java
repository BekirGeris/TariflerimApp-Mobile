package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.model.api.User;
import com.begers.tariflerim.service.http.abstracts.UserAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private final String  BASE_URL = "http://192.168.1.104:9999/";


    Gson gson = new GsonBuilder().setLenient().create();

    private UserAPI api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UserAPI.class);

    public Observable<DataResult<List<User>>> getAll(){
        return api.getAll();
    }

    public Observable<DataResult<User>> getUserWithUserId(int userId){
        return api.getUserWithUserId(userId);
    }

    public Observable<DataResult<User>> getUserWithEmailAndPassword(String email, String password){
        return api.getUserWithEmailAndPassword(email, password);
    }

    public Observable<Result> add(User user){
        return api.add(user);
    }
}
