package com.tellioglu.mapjava.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tellioglu.mapjava.databinding.RecyclerRowBinding;
import com.tellioglu.mapjava.model.MyPlace;
import com.tellioglu.mapjava.view.MapsActivity;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

    List<MyPlace> myPlaceList;

    public PlaceAdapter(List<MyPlace> myPlaceList) {
        this.myPlaceList = myPlaceList;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PlaceHolder(recyclerRowBinding) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.binding.recyclerTextView.setText(myPlaceList.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                    intent.putExtra("info","old");
                    intent.putExtra("place", myPlaceList.get(position));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return myPlaceList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding binding;
        public PlaceHolder(RecyclerRowBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
