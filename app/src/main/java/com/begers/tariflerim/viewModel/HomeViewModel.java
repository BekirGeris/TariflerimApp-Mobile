package com.begers.tariflerim.viewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.service.abstracts.TarifDao;
import com.begers.tariflerim.service.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.TarifComparator;
import com.begers.tariflerim.view.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

public class HomeViewModel extends BaseViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TarifDatabase db;
    private TarifDao tarifDao;

    private MutableLiveData<List<Tarif>> tarifs = new MutableLiveData<>();
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

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
                    List<Tarif> sortedList = new ArrayList<>(unSortedList);
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

    public MutableLiveData<List<Tarif>> getTarifs() {
        return tarifs;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}