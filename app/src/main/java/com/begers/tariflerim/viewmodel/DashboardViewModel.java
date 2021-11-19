package com.begers.tariflerim.viewmodel;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.model.api.TarifR;
import com.begers.tariflerim.model.dtos.RecipeDto;
import com.begers.tariflerim.model.roomdb.Tarif;
import com.begers.tariflerim.service.http.concoretes.RecipeService;
import com.begers.tariflerim.service.local.abstracts.TarifDao;
import com.begers.tariflerim.service.local.concoretes.TarifDatabase;
import com.begers.tariflerim.view.ui.dashboard.DashboardFragmentDirections;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DashboardViewModel extends BaseViewModel {

    private RecipeService recipeService = new RecipeService();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TarifDatabase db;
    private TarifDao tarifDao;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();

        db = TarifDatabase.getInstance(application.getApplicationContext());
        tarifDao = db.tarifDao();

    }

    public void insertTarif(Tarif tarif, View view){
        compositeDisposable.add(tarifDao.insert(tarif)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Toast.makeText(getApplication(), "Tarif Yayınlandı", Toast.LENGTH_LONG).show();
                        NavDirections action = DashboardFragmentDirections.actionNavigationDashboardToNavigationHome();
                        Navigation.findNavController(view).navigate(action);
                    }
                })
        );
    }

    public void addTarif(TarifR tarifR, View view){
        recipeService.add(tarifR)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("add");
                        NavDirections action = DashboardFragmentDirections.actionNavigationDashboardToNavigationHome();
                        Navigation.findNavController(view).navigate(action);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}