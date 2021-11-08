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
import com.begers.tariflerim.databinding.FragmentSigninBinding;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.viewmodel.LoginViewModel;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;
    private LoginViewModel viewModel;

    public SignInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn(v);
            }
        });

        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        observerLiveData();
    }

    private void observerLiveData() {
        viewModel.getIsCheckRecord().observe(getViewLifecycleOwner(), isCheckRecord -> {
            if (isCheckRecord) {
                Toast.makeText(getActivity(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                goMainActivity();
            }
        });
    }

    public void saveUser() {
        String firstName = binding.editTextFirstName.getText().toString();
        String lastName = binding.editTextLastName.getText().toString();
        String email = binding.editTextTextEmailAddressSignin.getText().toString();
        String password = binding.editTextTextPasswordSignin.getText().toString();
        String passwordAgain = binding.editTextTextPasswordAgain.getText().toString();

        if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("") || passwordAgain.equals("")){
            Toast.makeText(getActivity(), "Bilgileri tam doldurunuz", Toast.LENGTH_SHORT).show();
        }else {
            if (password.equals(passwordAgain)){
                User user = new User(firstName, lastName, email, password);

                viewModel.insertUser(user);
                viewModel.createSingletonUserWithEmailAndPassword(email, password);
            }else {
                Toast.makeText(getActivity(), "Parolaları aynı giriniz", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goLogIn(View view){
        NavDirections action = new ActionOnlyNavDirections(R.id.action_signInFragment_to_logInFragment3);
        Navigation.findNavController(view).navigate(action);
    }

    private void goMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}