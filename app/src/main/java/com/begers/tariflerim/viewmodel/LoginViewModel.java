package com.begers.tariflerim.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.begers.tariflerim.service.abstracts.UserDao;
import com.begers.tariflerim.service.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.view.ui.Login.LoginActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static androidx.core.content.ContextCompat.startActivity;

public class LoginViewModel extends BaseViewModel {

    private SharedPreferences preferences;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDatabase db;
    private UserDao userDao;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        preferences = application.getSharedPreferences("pref", Context.MODE_PRIVATE);
        db = UserDatabase.getInstance(getApplication());
        userDao = db.userDao();
    }

    public void createSingletonUser(LoginActivity activity, Bundle bundle){
        compositeDisposable.add(userDao.getUserId(preferences.getInt("userId", -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);
                    Intent intent = new Intent(activity, MainActivity.class);
                    startActivity(getApplication(), intent, bundle);
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
