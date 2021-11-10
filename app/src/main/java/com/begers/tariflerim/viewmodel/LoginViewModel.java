package com.begers.tariflerim.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.model.dtos.RecipeDto;
import com.begers.tariflerim.service.http.concoretes.RecipeService;
import com.begers.tariflerim.service.local.abstracts.UserDao;
import com.begers.tariflerim.service.local.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;

import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    private RecipeService recipeService = new RecipeService();

    private SharedPreferences preferences;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDatabase db;
    private UserDao userDao;

    private MutableLiveData<Boolean> isAccountActive = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCheckEmailAndPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCheckRecord = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        preferences = application.getSharedPreferences("pref", Context.MODE_PRIVATE);
        db = UserDatabase.getInstance(getApplication());
        userDao = db.userDao();

        /*
        compositeDisposable.add(recipeService.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Tarif>>() {
                    @Override
                    public void accept(List<Tarif> tarifs) throws Throwable {
                        System.out.println(tarifs.get(0).getName());
                    }
                }));          */
    }

    public void createSingletonUserWithPreferences(){
        compositeDisposable.add(userDao.getUserId(preferences.getInt("userId", -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);
                    isAccountActive.setValue(true);
                }));
    }

    public void checkEmailAndPassword(String email, String password){
        compositeDisposable.add(userDao.getBoolEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    isCheckEmailAndPassword.setValue(result);
                })
        );
    }

    public void createSingletonUserWithEmailAndPassword(String email, String password) {
        compositeDisposable.add(userDao.getUserEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);
                    isCheckEmailAndPassword.setValue(true);
                    preferences.edit().putInt("userId", user.getId()).apply();
                })
        );
    }

    public void insertUser(User user){
        compositeDisposable.add(userDao.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isCheckRecord.setValue(true);
                    }
                })
        );
    }

    public MutableLiveData<Boolean> getIsAccountActive() {
        return isAccountActive;
    }

    public MutableLiveData<Boolean> getIsCheckEmailAndPassword() {
        return isCheckEmailAndPassword;
    }

    public MutableLiveData<Boolean> getIsCheckRecord() {
        return isCheckRecord;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
