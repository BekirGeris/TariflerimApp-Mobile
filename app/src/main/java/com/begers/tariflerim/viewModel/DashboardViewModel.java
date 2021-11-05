package com.begers.tariflerim.viewModel;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.service.abstracts.TarifDao;
import com.begers.tariflerim.service.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.ui.dashboard.DashboardFragmentDirections;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DashboardViewModel extends BaseViewModel {

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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}