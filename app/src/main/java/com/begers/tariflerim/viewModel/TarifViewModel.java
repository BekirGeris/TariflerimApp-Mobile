package com.begers.tariflerim.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.repository.TarifRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class TarifViewModel extends AndroidViewModel {

    private TarifRepository tarifRepository;

    public TarifViewModel(@NonNull Application application) {
        super(application);
        tarifRepository = new TarifRepository(application);
    }

    public Flowable<List<Tarif>> getAllTarif(){
        return tarifRepository.getAll();
    }

    public void insert(Tarif tarif){
        tarifRepository.insert(tarif);
    }

    public void update(Tarif tarif){
        tarifRepository.update(tarif);
    }

    public void delete(Tarif tarif){
        tarifRepository.delete(tarif);
    }
}
