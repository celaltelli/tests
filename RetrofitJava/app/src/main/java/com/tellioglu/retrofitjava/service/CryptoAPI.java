package com.tellioglu.retrofitjava.service;

import com.tellioglu.retrofitjava.model.CryptoModel;
import com.tellioglu.retrofitjava.model.CurrencyModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface CryptoAPI {

    //https://api.nomics.com/v1

    // https://api.nomics.com/v1/prices?key=43808c5e98edc700a28ad22f47caf1592b8ba687


    @GET("prices?key=43808c5e98edc700a28ad22f47caf1592b8ba687")
    Observable<List<CryptoModel>> getData();

    @GET("kurgetir")
    Observable<CurrencyModel> getCurrencyData();
  //  public Call<List<CryptoModel>> getData();
}
