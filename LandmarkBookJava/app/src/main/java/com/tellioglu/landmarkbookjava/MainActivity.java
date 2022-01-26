package com.tellioglu.landmarkbookjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tellioglu.landmarkbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    ArrayList<Lendmark> lendmarkArrayList;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        lendmarkArrayList = new ArrayList<>();

        Lendmark pisa = new Lendmark("pisa", "Italy", R.drawable.pisa);
        Lendmark eyfel = new Lendmark("eyfel", "France", R.drawable.eyfel);
        Lendmark kizkulesi = new Lendmark("kizkulesi", "Turkey", R.drawable.kizkulesi);

        lendmarkArrayList.add(pisa);
        lendmarkArrayList.add(eyfel);
        lendmarkArrayList.add(kizkulesi);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LandmarkAdapter landmarkAdapter  = new LandmarkAdapter(lendmarkArrayList);
        binding.recyclerView.setAdapter(landmarkAdapter);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
//                lendmarkArrayList.stream().map(lendmark -> lendmark.name).collect(Collectors.toList()));
//        binding.landmarkListView.setAdapter(arrayAdapter);
//        binding.landmarkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra("lendmark", lendmarkArrayList.get(position));
//                startActivity(intent);
//            }
//        });
    }
}