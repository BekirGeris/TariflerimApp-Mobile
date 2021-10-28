package com.begers.tariflerim.view.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.begers.tariflerim.R;
import com.begers.tariflerim.databinding.FragmentDashboardBinding;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.roomdb.abstracts.TarifDao;
import com.begers.tariflerim.roomdb.concoretes.TarifDatabase;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.view.MainActivity;
import com.begers.tariflerim.view.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private CompositeDisposable compositeDisposable;

    private ActivityResultLauncher<Intent> activityResultLauncher;  //galeriye gitmek için kullanılır
    private ActivityResultLauncher<String> permissionLauncher;  //izin almak için kullanılır.
    private Bitmap selectedImage;

    private TarifDatabase db;
    private TarifDao tarifDao;

    private SingletonUser singletonUser;
    private User user;

    public DashboardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singletonUser = SingletonUser.getInstance();
        user = singletonUser.getSentUser();

        compositeDisposable = new CompositeDisposable();

        db = TarifDatabase.getInstance(getContext());
        tarifDao = db.tarifDao();

        registerLauncher();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setScr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage(view);
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(view);
            }
        });
    }

    public void save(View view){
        String tarifName = binding.textName.getText().toString();
        String tarifDec = binding.textDescription.getText().toString();

        if (tarifName.equals("") || tarifDec.equals("") || selectedImage == null){
            Toast.makeText(getActivity(), "Bilgileri Boş Bırakmayınız", Toast.LENGTH_LONG).show();
        }else {
            Bitmap smallImage = makeSmallerImage(selectedImage,300);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
            byte[] bytes = outputStream.toByteArray();

            Tarif tarif = new Tarif(tarifName, user.getId(), tarifDec, bytes);

            compositeDisposable.add(tarifDao.insert(tarif)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Throwable {
                            Toast.makeText(getActivity(), "Tarif Yayınlandı", Toast.LENGTH_LONG).show();
                            NavDirections action = DashboardFragmentDirections.actionNavigationDashboardToNavigationHome();
                            Navigation.findNavController(view).navigate(action);
                        }
                    })
            );
        }
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

    private void registerLauncher(){  //tanımlamalar yapılacak

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null){
                        Uri imageData = intentFromResult.getData(); //kullanıcının seçtiği resmin kaynağını verir.
                        binding.setScr.setImageURI(imageData);

                        try{
                            if (Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                            }else {
                                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageData);
                            }
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
}