package com.begers.tariflerim.view.ui.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.room.Room;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentSigninBinding;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.UserDao;
import com.begers.tariflerim.roomdb.concoretes.TarifDatabase;
import com.begers.tariflerim.roomdb.concoretes.UserDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private UserDatabase db;
    private UserDao userDao;

    public SignInFragment(){

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

    public void saveUser() {
        String fisrtName = binding.editTextFirstName.getText().toString();
        String lastName = binding.editTextLastName.getText().toString();
        String email = binding.editTextTextEmailAddressSignin.getText().toString();
        String password = binding.editTextTextPasswordSignin.getText().toString();
        String passwordAgain = binding.editTextTextPasswordAgain.getText().toString();
        if (fisrtName.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(getActivity(), "Bilgileri tam doldurunuz", Toast.LENGTH_SHORT).show();
        }else {
            if (password.equals(passwordAgain) == false){
                Toast.makeText(getActivity(), "Parolaları aynı giriniz", Toast.LENGTH_SHORT).show();
            }else {
                User user = new User(fisrtName, lastName, email, password);
                compositeDisposable.add(userDao.insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Throwable {
                        Toast.makeText(getActivity(),   "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    }
                })
                );
            }
        }

    }
}