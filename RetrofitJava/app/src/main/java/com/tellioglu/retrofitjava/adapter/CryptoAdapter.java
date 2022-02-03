package com.tellioglu.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tellioglu.retrofitjava.databinding.RecyclerRowBinding;
import com.tellioglu.retrofitjava.model.CryptoModel;

import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.RowHolder> {


    List<CryptoModel> cryptoModelList;
    String[] colors = {"#005f73", "#e9d8a6", "#ee9b00", "#cfbaf0", "#90dbf4", "#b9fbc0","#b7b7a4", "#fca311"};


    public CryptoAdapter(List<CryptoModel> cryptoModelList) {
        this.cryptoModelList = cryptoModelList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RowHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        if(cryptoModelList ==null){
            holder.recyclerRowBinding.textName.setText("Bilgiler alinamadi");
            return;
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
        holder.recyclerRowBinding.textName.setText(cryptoModelList.get(position).getName());
      //  holder.recyclerRowBinding.textName.setBackgroundColor(Color.parseColor(colors[position % 9]));
        holder.recyclerRowBinding.textPrice.setText(cryptoModelList.get(position).getPrice());
      //  holder.recyclerRowBinding.textPrice.setBackgroundColor(Color.parseColor(colors[position % 9]));

    }

    @Override
    public int getItemCount() {
        return cryptoModelList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        RecyclerRowBinding recyclerRowBinding;

        public RowHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
