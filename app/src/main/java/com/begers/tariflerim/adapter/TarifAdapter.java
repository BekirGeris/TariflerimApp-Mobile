package com.begers.tariflerim.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.begers.tariflerim.databinding.RecyclerRowBinding;
import com.begers.tariflerim.model.Image;
import com.begers.tariflerim.model.Tarif;
import com.begers.tariflerim.model.User;
import com.begers.tariflerim.service.abstracts.ImageDao;
import com.begers.tariflerim.service.abstracts.UserDao;
import com.begers.tariflerim.service.concoretes.ImageDatabase;
import com.begers.tariflerim.service.concoretes.UserDatabase;

import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TarifAdapter extends RecyclerView.Adapter<TarifAdapter.TarifHolder> {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<Tarif> tarifs;

    Context context;

    private UserDatabase userDatabase;
    private UserDao userDao;

    private ImageDatabase imageDatabase;
    private ImageDao imageDao;

    public TarifAdapter(List<Tarif> tarifs, Context context) {
        this.tarifs = tarifs;
        this.context = context;

        userDatabase = Room.databaseBuilder(context, UserDatabase.class, "User").build();
        userDao = userDatabase.userDao();

        imageDatabase = Room.databaseBuilder(context, ImageDatabase.class, "Image").build();
        imageDao = imageDatabase.imageDao();
    }

    public class TarifHolder extends RecyclerView.ViewHolder{

        private RecyclerRowBinding binding;

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

        compositeDisposable.add(userDao.getUserId(tarifs.get(position).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                               @Override
                               public void accept(User user) throws Throwable {
                                   User userOfPost = user;
                                   holder.binding.textUserName.setText(userOfPost.getFirstName() + " " + userOfPost.getLastName());
                               }
                           })
                );

        compositeDisposable.add(imageDao.getImageUserId(tarifs.get(position).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Image>() {
                    @Override
                    public void accept(Image image) throws Throwable {
                        Image imageOfPost = image;
                        Bitmap bitmapPP = BitmapFactory.decodeByteArray(imageOfPost.getProfileImage(),0, imageOfPost.getProfileImage().length);
                        holder.binding.circleUserPp.setImageBitmap(bitmapPP);
                    }
                })
        );

        Bitmap bitmap = BitmapFactory.decodeByteArray(tarifs.get(position).getImage(),0,tarifs.get(position).getImage().length);
        holder.binding.recyclerViewImageView.setImageBitmap(bitmap);
        holder.binding.recyclerViewTitle.setText(tarifs.get(position).getName());
        holder.binding.recyclerViewDescription.setText(tarifs.get(position).getTarif());
        holder.binding.postDate.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return tarifs.size();
    }
}
