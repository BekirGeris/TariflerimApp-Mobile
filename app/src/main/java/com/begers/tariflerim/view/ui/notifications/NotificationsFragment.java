package com.begers.tariflerim.view.ui.notifications;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.begers.tariflerim.adapter.TarifAdapter;
import com.begers.tariflerim.adapter.TarifGritAdapter;
import com.begers.tariflerim.databinding.FragmentNotificationsBinding;
import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.ImageDao;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;
import com.begers.tariflerim.roomdb.abstracts.UserDao;
import com.begers.tariflerim.roomdb.concoretes.ImageDatabase;
import com.begers.tariflerim.roomdb.concoretes.TarifDatabase;
import com.begers.tariflerim.roomdb.concoretes.UserDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private UserDatabase userDatabase;
    private UserDao userDao;

    private ImageDatabase imageDatabase;
    private ImageDao imageDao;

    private TarifDatabase tarifDatabase;
    private TarifDao tarifDao;

    private SingletonUser singletonUser;
    private User user;

    private ActivityResultLauncher<Intent> activityResultLauncher;  //galeriye gitmek için kullanılır
    private ActivityResultLauncher<String> permissionLauncher;  //izin almak için kullanılır.
    private Bitmap selectedImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singletonUser = SingletonUser.getInstance();
        user = singletonUser.getSentUser();

        userDatabase = Room.databaseBuilder(getContext(), UserDatabase.class, "User").build();
        userDao = userDatabase.userDao();

        imageDatabase = Room.databaseBuilder(getContext(), ImageDatabase.class, "Image").build();
        imageDao = imageDatabase.imageDao();

        tarifDatabase = Room.databaseBuilder(getContext(), TarifDatabase.class, "Tarifler").build();
        tarifDao = tarifDatabase.tarifDao();

        compositeDisposable.add(userDao.getUserId(user.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(NotificationsFragment.this::handleResponseUser)
        );

        compositeDisposable.add(imageDao.getImageUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(NotificationsFragment.this::handleResponseImage)
        );

        compositeDisposable.add(tarifDao.getTarifsUserId(user.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(NotificationsFragment.this::handleResponseTarif)
        );
    }

    public void handleResponseUser(User user){
        binding.userName.setText(user.getFirstName());
    }

    public void handleResponseImage(Image image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image.getProfileImage(), 0, image.getProfileImage().length);
        binding.circleImageView.setImageBitmap(bitmap);
    }

    public void handleResponseTarif(List<Tarif> tarifs){
        binding.tarifSize.setText("Tarif Sayısı: " + tarifs.size());
        binding.recyclerViewGrit.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        TarifGritAdapter tarifGritAdapter = new TarifGritAdapter(tarifs);
        binding.recyclerViewGrit.setAdapter(tarifGritAdapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerLauncher();
        binding.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage(view);
            }
        });

    }

    public void selectedImage(View view){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){ //izin verilmedi izinin istenilme sebebi gösterilsin mi
                //izin ikinci kez verilmemiş
                Snackbar.make(view, "Galeriye gitmek için izniniz gerekiyor.", Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }else {
                //izin verilmemiş
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }else{
            //izin verilmiş
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);
        }
    }

    private void save(){
        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] bytes = outputStream.toByteArray();

        Image image = new Image(user.getId(), bytes);

        compositeDisposable.add(imageDao.insert(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    private void registerLauncher(){  //tanımlamalar yapılacak

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null){
                        Uri imageData = intentFromResult.getData(); //kullanıcının seçtiği resmin kaynağını verir.
                        binding.circleImageView.setImageURI(imageData);

                        Toast.makeText(getActivity(), "Seçim Tamamlandı", Toast.LENGTH_LONG).show();

                        try{
                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                            }else {
                                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageData);
                            }
                            save();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    //izin verildi
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }else{
                    //izin verilmedi
                    Toast.makeText(getActivity(), "İzin verilmedi", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image,width,height,true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}