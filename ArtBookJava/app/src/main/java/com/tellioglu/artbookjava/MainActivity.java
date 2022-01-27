package com.tellioglu.artbookjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tellioglu.artbookjava.databinding.ActivityArtBinding;
import com.tellioglu.artbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Picture> pictureArrayList;
    PictureAdapter pictureAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        pictureArrayList = new ArrayList<>();
        binding.recyclerView.setLayoutManager( new LinearLayoutManager(this));
        pictureAdapter = new PictureAdapter(pictureArrayList);
        binding.recyclerView.setAdapter(pictureAdapter);
        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.art_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_art){
            Intent intent = new Intent(this,ArtActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void getData(){
        try{
            SQLiteDatabase database  = this.openOrCreateDatabase("Pictures",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM pictures", null);
            int NameIx = cursor.getColumnIndex("pictureName");
            int intIx = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                String name = cursor.getString(NameIx);
                int id = cursor.getInt(intIx);
                Picture picture = new Picture(name,id);
                pictureArrayList.add(picture);
            }
            pictureAdapter.notifyDataSetChanged();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}