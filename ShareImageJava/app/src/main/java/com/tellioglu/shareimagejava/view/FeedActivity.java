package com.tellioglu.shareimagejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tellioglu.shareimagejava.R;
import com.tellioglu.shareimagejava.adapter.PostAdapter;
import com.tellioglu.shareimagejava.databinding.ActivityFeedBinding;
import com.tellioglu.shareimagejava.model.Post;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    ActivityFeedBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Post> postArrayList;
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postArrayList = new ArrayList<>();

        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);
    }

    private  void getData(){

       firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                else{
                    if(value != null){

                       for( DocumentSnapshot documentSnapshot : value.getDocuments()){

                           Map<String, Object> data = documentSnapshot.getData();
                           String useremail = (String) data.get("useremail");
                           String comment = (String) data.get("comment");
                           String downloadUrl = (String) data.get("downloadUrl");

                           Post post = new Post(useremail,comment,downloadUrl);
                           postArrayList.add(post);
                       }
                       postAdapter.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addPost){
            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.signOut){

            Intent intent = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            auth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}