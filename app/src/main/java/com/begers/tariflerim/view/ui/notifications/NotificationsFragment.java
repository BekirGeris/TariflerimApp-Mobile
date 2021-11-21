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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.begers.tariflerim.adapter.TarifGritAdapter;
import com.begers.tariflerim.databinding.FragmentNotificationsBinding;
import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.roomdb.User;
import com.begers.tariflerim.utiles.SingletonUser;
import com.begers.tariflerim.viewmodel.NotificationsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel viewModel;
    private FragmentNotificationsBinding binding;

    private SingletonUser singletonUser;
    private User user;

    private ActivityResultLauncher<Intent> activityResultLauncher;  //galeriye gitmek için kullanılır
    private ActivityResultLauncher<String> permissionLauncher;  //izin almak için kullanılır.
    private Bitmap selectedImage;

    private Uri imageData;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();

        registerLauncher();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerViewGrit.setLayoutManager(new GridLayoutManager(this.getContext(), 3));

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        singletonUser = SingletonUser.getInstance();
        user = singletonUser.getSentUser();

        binding.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage(view);
            }
        });

        binding.userName.setText(user.getFirstName());

        viewModel.getByImage(user);

        viewModel.getByTarifs(user);

        observerLiveData();
    }

    private void observerLiveData(){
        viewModel.getTarifs().observe(getViewLifecycleOwner(), tarifs -> {
            binding.tarifSize.setText("Tarif Sayısı: " + tarifs.size());
            TarifGritAdapter tarifGritAdapter = new TarifGritAdapter(tarifs);
            binding.recyclerViewGrit.setAdapter(tarifGritAdapter);
        });

        /*
        viewModel.getImageRoom().observe(getViewLifecycleOwner(), image -> {
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image.getProfileImage(), 0, image.getProfileImage().length);
                binding.circleImageView.setImageBitmap(bitmap);
            }
        });*/

        viewModel.getImage().observe(getViewLifecycleOwner(), image -> {
            Picasso.get().load(image.getImageURL()).into(binding.circleImageView);
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
        /*
        Bitmap smallImage = makeSmallerImage(selectedImage,300);

        image save roomdb
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] bytes = outputStream.toByteArray();

        ImageRoom imageRoom = new ImageRoom(user.getId(), bytes);
        viewModel.insertImage(imageRoom, binding.getRoot());
         */

        //Firebase and psql
        UUID uuid = UUID.randomUUID();
        String imageName = "images/" + uuid + ".jpg";
        storageReference.child(imageName).putFile(imageData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        StorageReference newReference = firebaseStorage.getReference(imageName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                viewModel.insertImage( new Image(user.getId(), uri.toString()));
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void registerLauncher(){  //tanımlamalar yapılacak

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null){
                        imageData = intentFromResult.getData(); //kullanıcının seçtiği resmin kaynağını verir.
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

}