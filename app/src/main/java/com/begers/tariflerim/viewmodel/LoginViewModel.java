package com.begers.tariflerim.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.model.api.User;
import com.begers.tariflerim.service.http.concoretes.UserService;
import com.begers.tariflerim.service.local.abstracts.UserDao;
import com.begers.tariflerim.service.local.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LoginViewModel extends BaseViewModel {

    private UserService userService = new UserService();

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
    }

    public void createSingletonUserWithPreferences(){
        /*
        compositeDisposable.add(userDao.getUserId(preferences.getInt("userId", -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);
                    isAccountActive.setValue(true);
                }));
         */

        userService.getUserWithUserId(preferences.getInt("userId", -1))
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<User> userDataResult) {
                        if (userDataResult.getData() != null){
                            SingletonUser singletonUser = SingletonUser.getInstance();
                            singletonUser.setSentUser(userDataResult.getData());
                            isAccountActive.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void checkEmailAndPassword(String email, String password){
        /*
        compositeDisposable.add(userDao.getBoolEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    isCheckEmailAndPassword.setValue(result);
                })
        );
         */

        userService.getUserWithEmailAndPassword(email, password)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<User> userDataResult) {
                        System.out.println(userDataResult.getMessage());
                        if (userDataResult.getData() != null){
                            isCheckEmailAndPassword.setValue(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createSingletonUserWithEmailAndPassword(String email, String password) {
        /*
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
         */

        userService.getUserWithEmailAndPassword(email, password)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<User> userDataResult) {
                        System.out.println(userDataResult.getMessage());
                        if (userDataResult.getData() != null){
                            SingletonUser singletonUser = SingletonUser.getInstance();
                            singletonUser.setSentUser(userDataResult.getData());
                            isCheckEmailAndPassword.setValue(true);
                            preferences.edit().putInt("userId", userDataResult.getData().getId()).apply();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void insertUser(User user){
        /*
        compositeDisposable.add(userDao.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isCheckRecord.setValue(true);
                    }
                })
        );*/

        userService.add(user)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        isCheckRecord.setValue(true);
                        System.out.println(result.getMessage());
                        createSingletonUserWithEmailAndPassword(user.getEmail(), user.getPassword());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
