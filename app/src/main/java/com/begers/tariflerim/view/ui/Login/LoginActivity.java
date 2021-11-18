package com.begers.tariflerim.view.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.begers.tariflerim.databinding.ActivityLoginBinding;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            auth.createUserWithEmailAndPassword("bekir.geris@gmail.com", "123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    System.out.println("create");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
            });
        }else {
            auth.signInWithEmailAndPassword("bekir.geris@gmail.com", "123456").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    System.out.println("sign in");
                }
            });
        }

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