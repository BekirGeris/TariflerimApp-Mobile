package com.begers.tariflerim.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.begers.tariflerim.databinding.RecyclerGritBinding;
import com.begers.tariflerim.model.Tarif;

import java.util.List;

public class TarifGritAdapter extends RecyclerView.Adapter<TarifGritAdapter.TarifGritHolder> {

    List<Tarif> tarifs;

    public TarifGritAdapter(List<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    class TarifGritHolder extends RecyclerView.ViewHolder{

        private RecyclerGritBinding binding;

        public TarifGritHolder(RecyclerGritBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public TarifGritHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerGritBinding recyclerGritBinding = RecyclerGritBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TarifGritHolder(recyclerGritBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TarifGritHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(tarifs.get(position).getImage(),0,tarifs.get(position).getImage().length);
        holder.binding.imageViewGrit.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return tarifs.size();
    }



}
