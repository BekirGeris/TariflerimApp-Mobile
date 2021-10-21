package com.begers.tariflerim.view.ui.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentSigninBinding;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;

    public SignInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogIn(v);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    public void goLogIn(View view){
        NavDirections action = new ActionOnlyNavDirections(R.id.action_signInFragment_to_logInFragment3);
        Navigation.findNavController(view).navigate(action);
    }
}
