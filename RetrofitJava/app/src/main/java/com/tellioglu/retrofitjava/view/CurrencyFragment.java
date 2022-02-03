package com.tellioglu.retrofitjava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tellioglu.retrofitjava.R;
import com.tellioglu.retrofitjava.adapter.CryptoAdapter;
import com.tellioglu.retrofitjava.adapter.CurrencyAdapter;
import com.tellioglu.retrofitjava.databinding.ActivityCurrencyBinding;
import com.tellioglu.retrofitjava.databinding.ActivityMainBinding;
import com.tellioglu.retrofitjava.model.CryptoModel;
import com.tellioglu.retrofitjava.model.CurrencyModel;
import com.tellioglu.retrofitjava.service.CryptoAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyFragment extends Fragment {

    List<CryptoModel> cryptoModelArrayList;
    ActivityCurrencyBinding binding;

    // final private String baseURL = "https://api.nomics.com/v1/";
    final private String baseURL = "http://hasanadiguzel.com.tr/api/";
    Retrofit retrofit;
    CurrencyAdapter currencyAdapter;

    CompositeDisposable compositeDisposable;
    public static CurrencyFragment getInstance(){
        return  new CurrencyFragment();
    }

    public CurrencyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityCurrencyBinding.inflate(inflater,container,false);

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

        compositeDisposable.add(cryptoAPI.getCurrencyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(this::handleError)
                .subscribe(this::handleResponse)

        );




    }
    private CurrencyModel handleError(Throwable throwable) {
        Toast.makeText(this.getContext(),"Error: "+ throwable.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        System.out.println(throwable.getMessage());
        return null;
    }
    void handleResponse(CurrencyModel currencyModel) {
        currencyAdapter = new CurrencyAdapter(currencyModel.currrencyModList);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(CurrencyFragment.this.getContext()));
        binding.recyclerView2.setAdapter(currencyAdapter);
    }
}