package com.begers.tariflerim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.begers.tariflerim.databinding.RecyclerRowBinding;
import com.begers.tariflerim.model.api.Image;
import com.begers.tariflerim.model.api.Tarif;
import com.begers.tariflerim.model.dtos.DataResult;
import com.begers.tariflerim.model.roomdb.User;
import com.begers.tariflerim.service.http.concoretes.ImageService;
import com.begers.tariflerim.service.http.concoretes.UserService;
import com.begers.tariflerim.service.local.abstracts.ImageDao;
import com.begers.tariflerim.service.local.abstracts.UserDao;
import com.begers.tariflerim.service.local.concoretes.ImageDatabase;
import com.begers.tariflerim.service.local.concoretes.UserDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class TarifAdapter extends RecyclerView.Adapter<TarifAdapter.TarifHolder> {

    private UserService userService = new UserService();
    private ImageService imageService = new ImageService();

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<Tarif> tarifs;

    Context context;

    private UserDatabase userDatabase;
    private UserDao userDao;

    private ImageDatabase imageDatabase;
    private ImageDao imageDao;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public TarifAdapter(List<Tarif> tarifs, Context context) {
        this.tarifs = tarifs;
        this.context = context;

        userDatabase = Room.databaseBuilder(context, UserDatabase.class, "User").build();
        userDao = userDatabase.userDao();

        imageDatabase = Room.databaseBuilder(context, ImageDatabase.class, "Image").build();
        imageDao = imageDatabase.imageDao();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
    }

    public static class TarifHolder extends RecyclerView.ViewHolder{

        private final RecyclerRowBinding binding;

        public TarifHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public TarifHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false );
        return new TarifHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TarifHolder holder, int position) {

        /*room
        compositeDisposable.add(userDao.getUserId(tarifs.get(position).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                               @Override
                               public void accept(User user) throws Throwable {
                                   holder.binding.textUserName.setText(user.getFirstName() + " " + user.getLastName());
                               }
                           })
                );

        compositeDisposable.add(imageDao.getImageUserId(tarifs.get(position).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ImageRoom>() {
                    @Override
                    public void accept(ImageRoom imageRoom) throws Throwable {
                        Bitmap bitmapPP = BitmapFactory.decodeByteArray(imageRoom.getProfileImage(),0, imageRoom.getProfileImage().length);
                        holder.binding.circleUserPp.setImageBitmap(bitmapPP);
                    }
                })
        );*/

        userService.getUserWithUserId(tarifs.get(position).getUserId())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<User> userDataResult) {
                        if (userDataResult.getData() != null){
                            holder.binding.textUserName.setText(userDataResult.getData().getFirstName() + " " + userDataResult.getData().getLastName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        imageService.getImageWithUserId(tarifs.get(position).getUserId())
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataResult<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataResult<Image> imageDataResult) {
                        if (imageDataResult.getData() != null){
                            Picasso.get().load(imageDataResult.getData().getImageURL()).into(holder.binding.circleUserPp);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*
        Bitmap bitmap = BitmapFactory.decodeByteArray(tarifRooms.get(position).getImage(),0,tarifRooms.get(position).getImage().length);
        holder.binding.recyclerViewImageView.setImageBitmap(bitmap);
         */

        Picasso.get().load(tarifs.get(position).getImageURL()).into(holder.binding.recyclerViewImageView);
        holder.binding.recyclerViewTitle.setText(tarifs.get(position).getName());
        holder.binding.recyclerViewDescription.setText(tarifs.get(position).getTarif());
        holder.binding.postDate.setText(tarifs.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return tarifs.size();
    }
}
