package com.tellioglu.artbookjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tellioglu.artbookjava.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {

    ArrayList<Picture> pictureArrayList;

    public PictureAdapter(ArrayList<Picture> pictureArrayList) {
        this.pictureArrayList = pictureArrayList;
    }

    @NonNull
    @Override
    public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);
        return new PictureHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureHolder holder, @SuppressLint("RecyclerView") int position) {
 holder.binding.recyclerViewTextView.setText(pictureArrayList.get(position).name);
 holder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent intent = new Intent(holder.itemView.getContext(),ArtActivity.class);
         intent.putExtra("info","old");
         intent.putExtra("pictureId",pictureArrayList.get(position).id);
         holder.itemView.getContext().startActivity(intent);
     }
 });
    }

    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }

    public class PictureHolder extends  RecyclerView.ViewHolder{
        RecyclerRowBinding binding;
        public PictureHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
