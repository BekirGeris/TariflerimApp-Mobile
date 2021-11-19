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
import com.begers.tariflerim.viewmodel.HomeViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
        viewModel.getAllTarifFromAPI();

        observerLiveData();
    }

    private void observerLiveData() {
        /*
        viewModel.getTarifs().observe(getViewLifecycleOwner(), tarifs -> {
            if (tarifs != null){
                adapter = new TarifAdapter(tarifs, getContext());
                binding.recyclerView.setAdapter(adapter);
            }
        });
         */

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {

        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {

        });

        viewModel.getTarifRs().observe(getViewLifecycleOwner(), tarifRs -> {
            if (tarifRs != null){
                adapter = new TarifAdapter(tarifRs, getContext());
                binding.recyclerView.setAdapter(adapter);
            }
        });
    }

}