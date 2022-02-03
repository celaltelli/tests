package com.tellioglu.retrofitjava.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tellioglu.retrofitjava.adapter.CryptoAdapter;
import com.tellioglu.retrofitjava.databinding.ActivityMainBinding;
import com.tellioglu.retrofitjava.model.CryptoModel;
import com.tellioglu.retrofitjava.service.CryptoAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CryptoFragment extends Fragment {

    List<CryptoModel> cryptoModelArrayList;
    ActivityMainBinding binding;

    final private String baseURL = "https://api.nomics.com/v1/";
    Retrofit retrofit;
    CryptoAdapter cryptoAdapter;

    CompositeDisposable compositeDisposable;
    // public String jsonString =  "https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json";

    public static CryptoFragment getInstance(){
        return  new CryptoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(inflater,container,false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().
                baseUrl(baseURL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        loadData();
    }

    private void loadData() {

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(this::handleError)
                .subscribe(this::handleResponse)

        );


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

    private List<CryptoModel> handleError(Throwable throwable) {
        Toast.makeText(this.getContext(),"Error: "+ throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        System.out.println(throwable.getMessage());
        return null;
    }

    void handleResponse(List<CryptoModel> cryptoModelList) {
        cryptoAdapter = new CryptoAdapter(cryptoModelList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(CryptoFragment.this.getContext()));
        binding.recyclerView.setAdapter(cryptoAdapter);

    }
}