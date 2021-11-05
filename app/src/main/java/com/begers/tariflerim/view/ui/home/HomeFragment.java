package com.begers.tariflerim.view.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.begers.tariflerim.adapter.TarifAdapter;
import com.begers.tariflerim.databinding.FragmentHomeBinding;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.service.abstracts.TarifDao;
import com.begers.tariflerim.service.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.TarifComparator;
import com.begers.tariflerim.viewModel.HomeViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private HomeViewModel viewModel;
    private TarifAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.refreshData();

        observerLiveData();
    }

    private void observerLiveData() {
        viewModel.getTarifs().observe(getViewLifecycleOwner(), tarifs -> {
            if (tarifs != null){
                adapter = new TarifAdapter(tarifs, getContext());
                binding.recyclerView.setAdapter(adapter);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {

        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {

        });
    }

}