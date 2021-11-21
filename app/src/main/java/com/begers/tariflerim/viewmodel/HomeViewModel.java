package com.begers.tariflerim.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.RecipeDto;
import com.begers.tariflerim.model.roomdb.TarifRoom;
import com.begers.tariflerim.service.http.concoretes.RecipeService;
import com.begers.tariflerim.service.local.abstracts.TarifDao;
import com.begers.tariflerim.service.local.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.TarifComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {

    private RecipeService recipeService = new RecipeService();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TarifDatabase db;
    private TarifDao tarifDao;

    private MutableLiveData<List<TarifRoom>> tarifs = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<List<Tarif>> tarifRs = new MutableLiveData<>();

    public HomeViewModel(Application application){
        super(application);
        db = TarifDatabase.getInstance(application.getApplicationContext());
        tarifDao = db.tarifDao();
    }

    public void refreshData(){
        getDataSQLite();
    }

    public void getDataSQLite() {
        loading.setValue(true);

        compositeDisposable.add(tarifDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(unSortedList -> {
                    List<TarifRoom> sortedList = new ArrayList<>(unSortedList);
                    Collections.sort(sortedList, new TarifComparator());
                    return sortedList;
                })
                .subscribe(tarifs -> {
                    this.tarifs.setValue(tarifs);
                    error.setValue(false);
                    loading.setValue(false);
                })
        );
    }

    public void getAllTarifFromAPI(){
        recipeService.getAll()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecipeDto>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull io.reactivex.disposables.Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull RecipeDto recipeDto) {
                        tarifRs.setValue(recipeDto.getData());
                        System.out.println("onNext");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    public MutableLiveData<List<TarifRoom>> getTarifs() {
        return tarifs;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<List<Tarif>> getTarifRs() {
        return tarifRs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}