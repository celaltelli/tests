package com.tellioglu.retrofit2.service;

import com.tellioglu.retrofit2.model.CryptoModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface CryptoAPI {

    //https://api.nomics.com/v1

    // https://api.nomics.com/v1/prices?key=43808c5e98edc700a28ad22f47caf1592b8ba687


    @GET("prices?key=43808c5e98edc700a28ad22f47caf1592b8ba687")
    Observable<List<CryptoModel>> getData();
  //  public Call<List<CryptoModel>> getData();
}
