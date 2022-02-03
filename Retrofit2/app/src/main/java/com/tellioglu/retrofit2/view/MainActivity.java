package com.tellioglu.retrofit2.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tellioglu.retrofit2.adapter.CryptoAdapter;
import com.tellioglu.retrofit2.databinding.ActivityMainBinding;
import com.tellioglu.retrofit2.model.CryptoModel;
import com.tellioglu.retrofit2.service.CryptoAPI;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<CryptoModel> cryptoModelArrayList;
    ActivityMainBinding binding;

    private String baseURL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    CryptoAdapter cryptoAdapter;

    CompositeDisposable compositeDisposable;
   // public String jsonString =  "https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().
                baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        compositeDisposable = new CompositeDisposable();
        loadData();


    }

    private void loadData() {

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);




//        Call<List<CryptoModel>> call = cryptoAPI.getData();
//
//        call.enqueue(new Callback<List<CryptoModel>>() {
//            @Override
//            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
//
//                if(response.isSuccessful()){
//                    cryptoModelArrayList = response.body();
//                    cryptoAdapter = new CryptoAdapter(cryptoModelArrayList);
//                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    binding.recyclerView.setAdapter(cryptoAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
//                Toast.makeText(MainActivity.this.getApplicationContext(),"Data alinamadi: "+ t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
//
//            }
//        });

    }
    void handleResponse(List<CryptoModel> cryptoModelList)
    {
                    cryptoAdapter = new CryptoAdapter(cryptoModelArrayList);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    binding.recyclerView.setAdapter(cryptoAdapter);
    }
}