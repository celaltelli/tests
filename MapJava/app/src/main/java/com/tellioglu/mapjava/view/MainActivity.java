package com.tellioglu.mapjava.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.tellioglu.mapjava.R;
import com.tellioglu.mapjava.adapter.PlaceAdapter;
import com.tellioglu.mapjava.databinding.ActivityMainBinding;
import com.tellioglu.mapjava.model.MyPlace;
import com.tellioglu.mapjava.roomdb.PlaceDao;
import com.tellioglu.mapjava.roomdb.PlaceDataBase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    PlaceDataBase dataBase;
    PlaceDao placeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("info","new");
                startActivity(intent);
            }
        });

        dataBase = Room.databaseBuilder(getApplicationContext(),PlaceDataBase.class,"Places").build();
        placeDao = dataBase.placeDao();

        compositeDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(MainActivity.this::handleResponse)
        );
    }
    private void handleResponse (List<MyPlace> placesList){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new PlaceAdapter(placesList));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addPlace) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.travel_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}