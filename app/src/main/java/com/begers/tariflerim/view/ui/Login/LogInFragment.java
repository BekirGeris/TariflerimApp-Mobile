package com.begers.tariflerim.view.ui.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentLoginBinding;

public class LogInFragment extends Fragment {

    private FragmentLoginBinding binding;
    public LogInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.goSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn(v);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void goSignIn(View view) {
        NavDirections action = new ActionOnlyNavDirections(R.id.action_logInFragment_to_signInFragment2);
        Navigation.findNavController(view).navigate(action);
    }

    public void saveUser(){

    }
}
