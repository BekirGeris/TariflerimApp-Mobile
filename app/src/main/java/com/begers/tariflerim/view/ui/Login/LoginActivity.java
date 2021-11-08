package com.begers.tariflerim.view.ui.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.begers.tariflerim.databinding.ActivityLoginBinding;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.createSingletonUserWithPreferences();

        observerLiveData();
    }

    private void observerLiveData() {
        viewModel.getIsAccountActive().observe(this, isAccountActive -> {
            if (isAccountActive){
                binding.proBar.setVisibility(View.VISIBLE);
                goMainActivity();
            }
        });
    }

    private void goMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}