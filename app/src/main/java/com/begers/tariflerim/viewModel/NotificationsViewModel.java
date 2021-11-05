package com.begers.tariflerim.viewModel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.service.abstracts.ImageDao;
import com.begers.tariflerim.service.abstracts.TarifDao;
import com.begers.tariflerim.service.abstracts.UserDao;
import com.begers.tariflerim.service.concoretes.ImageDatabase;
import com.begers.tariflerim.service.concoretes.TarifDatabase;
import com.begers.tariflerim.service.concoretes.UserDatabase;
import com.begers.tariflerim.view.ui.notifications.NotificationsFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Tarif>> tarifs = new MutableLiveData<>();
    private MutableLiveData<Image> image = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private UserDatabase userDatabase;
    private UserDao userDao;

    private ImageDatabase imageDatabase;
    private ImageDao imageDao;

    private TarifDatabase tarifDatabase;
    private TarifDao tarifDao;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        userDatabase = UserDatabase.getInstance(getApplication());
        userDao = userDatabase.userDao();

        imageDatabase = ImageDatabase.getInstance(getApplication());
        imageDao = imageDatabase.imageDao();

        tarifDatabase = TarifDatabase.getInstance(getApplication());
        tarifDao = tarifDatabase.tarifDao();
    }

    public void getByImage(User user){
        compositeDisposable.add(imageDao.getImageUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    image.setValue(i);
                })
        );
    }

    public void getByTarifs(User user){
        compositeDisposable.add(tarifDao.getTarifsUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> {
                    tarifs.setValue(t);
                })
        );
    }

    public void insertImage(Image image, View view){

        compositeDisposable.add(imageDao.insert(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );

    }

    public MutableLiveData<List<Tarif>> getTarifs() {
        return tarifs;
    }

    public MutableLiveData<Image> getImage() {
        return image;
    }
}