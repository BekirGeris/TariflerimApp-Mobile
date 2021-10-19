package com.begers.tariflerim.view.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.begers.tariflerim.R;
import com.begers.tariflerim.adapter.TarifAdapter;
import com.begers.tariflerim.databinding.FragmentHomeBinding;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.roomdb.TarifDao;
import com.begers.tariflerim.roomdb.TarifDatabase;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.view.ui.dashboard.DashboardFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    TarifDatabase db;
    TarifDao tarifDao;

    private FragmentHomeBinding binding;

    private TarifAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();

        db = Room.databaseBuilder(getActivity(), TarifDatabase.class, "Tarifler").build();
        tarifDao = db.tarifDao();

        goster();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void goster(){
        compositeDisposable.add(tarifDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(HomeFragment.this::handleResponse)
        );
    }

    public void handleResponse(List<Tarif> tarifs){
        if (tarifs.size() > 0){
            adapter = new TarifAdapter(tarifs);
            binding.recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}