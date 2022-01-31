package com.tellioglu.shareimagejava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tellioglu.shareimagejava.databinding.RecyclerRowBinding;
import com.tellioglu.shareimagejava.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    ArrayList<Post> postArrayList;

    public PostAdapter(ArrayList<Post> postArrayList) {
        this.postArrayList = postArrayList;
    }


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        if(postArrayList.size()<= 0)
            return;
        holder.recyclerRowBinding.recyclerEmailText.setText(postArrayList.get(position).email);
        holder.recyclerRowBinding.recyclerCommentText.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).donwloadUrl).into(holder.recyclerRowBinding.recyclerViewImageView);

    }



    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class PostHolder extends  RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding ;


        public PostHolder(RecyclerRowBinding recyclerRowBinding ) {

            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
