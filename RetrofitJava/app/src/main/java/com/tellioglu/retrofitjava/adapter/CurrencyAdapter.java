package com.tellioglu.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tellioglu.retrofitjava.databinding.RecyclerRow2Binding;
import com.tellioglu.retrofitjava.model.CurrencyModel;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.RowHolder> {


    List<CurrencyModel.CurrrencyMod> currrencyModList;
    String[] colors = {"#005f73", "#e9d8a6", "#ee9b00", "#cfbaf0", "#90dbf4", "#b9fbc0","#b7b7a4", "#fca311"};


    public CurrencyAdapter(List<CurrencyModel.CurrrencyMod> currrencyModList) {
        this.currrencyModList = currrencyModList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRow2Binding recyclerRowBinding = RecyclerRow2Binding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RowHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        if(currrencyModList ==null){
            holder.recyclerRowBinding.textCurrencyName.setText("Bilgiler alinamadi");
            return;
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
        holder.recyclerRowBinding.textCurrencyName.setText(currrencyModList.get(position).getName());
        holder.recyclerRowBinding.textPriceGet.setText(currrencyModList.get(position).getBanknoteBuying());
        holder.recyclerRowBinding.textPriceSell.setText(currrencyModList.get(position).getBanknoteSelling());
      //  holder.recyclerRowBinding.textPriceDifference.setText(currencyModelList.get(position).get());


    }

    @Override
    public int getItemCount() {
        return currrencyModList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        RecyclerRow2Binding recyclerRowBinding;

        public RowHolder(RecyclerRow2Binding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
