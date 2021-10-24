package com.begers.tariflerim.view.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentLoginBinding;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;
import com.begers.tariflerim.roomdb.abstracts.UserDao;
import com.begers.tariflerim.roomdb.concoretes.TarifDatabase;
import com.begers.tariflerim.roomdb.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.MainActivity;

import java.util.List;
import java.util.prefs.Preferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class LogInFragment extends Fragment {

    private FragmentLoginBinding binding;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDatabase db;
    private UserDao userDao;

    public LogInFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getContext(), UserDatabase.class, "User").build();
        userDao = db.userDao();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        compositeDisposable.add(userDao.getUserEmailAndPassword(email, password)
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(getActivity(), "hata", Toast.LENGTH_SHORT).show();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(LogInFragment.this::handleResponse)
        );
    }

    public void handleResponse(User user){

        SingletonUser singletonUser = SingletonUser.getInstance();
        singletonUser.setSentUser(user);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
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

}
