package com.begers.tariflerim.view.ui.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.begers.tariflerim.R;
import com.begers.tariflerim.roomdb.abstracts.UserDao;
import com.begers.tariflerim.roomdb.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private UserDatabase db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "User").build();
        userDao = db.userDao();

        preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);

        compositeDisposable.add(userDao.getUserId(preferences.getInt("userId", -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    SingletonUser singletonUser = SingletonUser.getInstance();
                    singletonUser.setSentUser(user);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
        );
    }

}