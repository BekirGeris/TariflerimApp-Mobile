package com.begers.tariflerim.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.roomdb.TarifRoom;
import com.begers.tariflerim.service.http.concoretes.RecipeService;
import com.begers.tariflerim.service.local.abstracts.TarifDao;
import com.begers.tariflerim.service.local.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.TarifComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
                .subscribe(new Observer<DataResult<List<Tarif>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<List<Tarif>> listDataResult) {
                        tarifRs.setValue(listDataResult.getData());
                        System.out.println(listDataResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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