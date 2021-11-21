package com.begers.tariflerim.view.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentLoginBinding;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.viewmodel.LoginViewModel;


public class LogInFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    public LogInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.goSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn(v);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextTextEmailAddressLogin.getText().toString().equals("")
                        || binding.editTextTextPasswordLogin.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bilgileri tam giriniz", Toast.LENGTH_SHORT).show();
                }else {
                    girisYap();
                }
            }
        });

        observerLiveData();
    }

    private void observerLiveData() {
        viewModel.getIsCheckEmailAndPassword().observe(getViewLifecycleOwner(), isCheckEmailAndPasswordActive -> {
            if (isCheckEmailAndPasswordActive) {
                Toast.makeText(getActivity(), "Giriş Başarılı.", Toast.LENGTH_SHORT).show();
                goMainActivity();
            }else {
                Toast.makeText(getActivity(), "Kullanıcı Bulunamadı.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void girisYap(){
        String email = binding.editTextTextEmailAddressLogin.getText().toString();
        String password = binding.editTextTextPasswordLogin.getText().toString();

        viewModel.checkEmailAndPassword(email, password);
        viewModel.createSingletonUserWithEmailAndPassword(email, password);
    }

    public void goSignIn(View view) {
        NavDirections action = new ActionOnlyNavDirections(R.id.action_logInFragment_to_signInFragment2);
        Navigation.findNavController(view).navigate(action);
    }

    private void goMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
