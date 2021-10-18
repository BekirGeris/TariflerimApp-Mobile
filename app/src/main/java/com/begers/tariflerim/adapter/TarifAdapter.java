package com.begers.tariflerim.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.begers.tariflerim.databinding.RecyclerRowBinding;
import com.begers.tariflerim.model.Tarif;

import java.util.List;

public class TarifAdapter extends RecyclerView.Adapter<TarifAdapter.TarifHolder> {

    List<Tarif> tarifs;

    public TarifAdapter(List<Tarif> tarifs) {
        this.tarifs = tarifs;
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
        Bitmap bitmap = BitmapFactory.decodeByteArray(tarifs.get(position).getImage(),0,tarifs.get(position).getImage().length);
        holder.binding.recyclerViewImageView.setImageBitmap(bitmap);
        holder.binding.recyclerViewTitle.setText(tarifs.get(position).getName());
        holder.binding.recyclerViewDescription.setText(tarifs.get(position).getTarif());
    }

    @Override
    public int getItemCount() {
        return tarifs.size();
    }
}
