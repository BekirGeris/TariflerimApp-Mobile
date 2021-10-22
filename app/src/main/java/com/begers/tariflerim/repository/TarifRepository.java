package com.begers.tariflerim.repository;

import android.app.Application;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;
import com.begers.tariflerim.roomdb.concoretes.TarifDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TarifRepository {

    private TarifDao tarifDao;

    public TarifRepository(Application application){
        TarifDatabase tarifDatabase = TarifDatabase.getInstance(application);
        tarifDao = tarifDatabase.tarifDao();
    }

    public Flowable<List<Tarif>> getAll(){
        return tarifDao.getAll();
    }

    public void insert(Tarif tarif){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                tarifDao.insert(tarif);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void update(Tarif tarif){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                tarifDao.update(tarif);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void delete(Tarif tarif){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                tarifDao.delete(tarif);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
