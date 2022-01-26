package com.tellioglu.landmarkbookjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tellioglu.landmarkbookjava.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        Intent intent = getIntent();
//        Lendmark selectedLendmark = (Lendmark) ((Intent) intent).getSerializableExtra("lendmark");
        Singleton singleton = Singleton.getInstance();

        Lendmark selectedLendmark = singleton.getSentLendmark();
        binding.countryTextView.setText(selectedLendmark.country);
        binding.nameTextView.setText(selectedLendmark.name);
        binding.imageView.setImageResource(selectedLendmark.id);

    }
}