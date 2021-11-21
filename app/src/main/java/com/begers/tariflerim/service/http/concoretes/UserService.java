package com.begers.tariflerim.service.http.concoretes;

import com.begers.tariflerim.model.dtos.UserListDto;
import com.begers.tariflerim.model.dtos.UserDto;
import com.begers.tariflerim.model.roomdb.User;
import com.begers.tariflerim.service.http.abstracts.UserAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Completable;
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

    public Observable<UserListDto> getAll(){
        return api.getAll();
    }

    public Observable<UserDto> getUserWithUserId(int userId){
        return api.getUserWithUserId(userId);
    }

    public Observable<UserDto> getUserWithEmailAndPassword(String email, String password){
        return api.getUserWithEmailAndPassword(email, password);
    }

    public Completable add(User user){
        return api.add(user);
    }
}
