package com.begers.tariflerim.view.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentLoginBinding;
import com.begers.tariflerim.service.abstracts.UserDao;
import com.begers.tariflerim.service.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.MainActivity;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LogInFragment extends Fragment {

    private FragmentLoginBinding binding;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDatabase db;
    private UserDao userDao;

    private SharedPreferences preferences;

    public LogInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

        db = UserDatabase.getInstance(getContext());
        userDao = db.userDao();

        binding.goSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignIn(v);
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextTextEmailAddressLogin.getText().toString().equals("") || binding.editTextTextPasswordLogin.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bilgileri tam giriniz", Toast.LENGTH_SHORT).show();
                }else {
                    girisYap();
                }
            }
        });
    }

    public void girisYap(){
        String email = binding.editTextTextEmailAddressLogin.getText().toString();
        String password = binding.editTextTextPasswordLogin.getText().toString();

        compositeDisposable.add(userDao.getBoolEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result == true){
                        Toast.makeText(getActivity(), "Giriş Başarılı.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Kullanıcı Bulunamadı.", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        compositeDisposable.add(userDao.getUserEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);

                    preferences.edit().putInt("userId", user.getId()).apply();

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                })
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void goSignIn(View view) {
        NavDirections action = new ActionOnlyNavDirections(R.id.action_logInFragment_to_signInFragment2);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
