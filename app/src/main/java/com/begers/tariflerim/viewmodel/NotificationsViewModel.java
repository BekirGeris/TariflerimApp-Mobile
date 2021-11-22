package com.begers.tariflerim.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.dtos.Result;
import com.begers.tariflerim.model.roomdb.ImageRoom;
import com.begers.tariflerim.model.roomdb.TarifRoom;
import com.begers.tariflerim.model.roomdb.User;
import com.begers.tariflerim.service.http.concoretes.ImageService;
import com.begers.tariflerim.service.local.abstracts.ImageDao;
import com.begers.tariflerim.service.local.abstracts.TarifDao;
import com.begers.tariflerim.service.local.abstracts.UserDao;
import com.begers.tariflerim.service.local.concoretes.ImageDatabase;
import com.begers.tariflerim.service.local.concoretes.TarifDatabase;
import com.begers.tariflerim.service.local.concoretes.UserDatabase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationsViewModel extends AndroidViewModel {

    private ImageService imageService = new ImageService();

    private MutableLiveData<List<TarifRoom>> tarifs = new MutableLiveData<>();
    private MutableLiveData<ImageRoom> imageRoom = new MutableLiveData<>();
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
        /*
        compositeDisposable.add(imageDao.getImageUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    imageRoom.setValue(i);
                })
        );*/

        imageService.getImageWithUserId(user.getId())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<Image> imageDataResult) {
                        if (imageDataResult.getData() != null){
                            image.setValue(imageDataResult.getData());
                        }
                        System.out.println("getImageWithUserId onNext");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    public void insertImage(Image image){

        /*
        compositeDisposable.add(imageDao.insert(imageRoom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
         */

        imageService.getImageWithUserId(image.getUserId())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<Image> imageDataResult) {
                        if (imageDataResult.getData() == null){
                            addImage(image);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void addImage(Image image){
        imageService.add(image)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Result result) {
                        System.out.println(result.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<List<TarifRoom>> getTarifs() {
        return tarifs;
    }

    public MutableLiveData<ImageRoom> getImageRoom() {
        return imageRoom;
    }

    public MutableLiveData<Image> getImage() {
        return image;
    }
}