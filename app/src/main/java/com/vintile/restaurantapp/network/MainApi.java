package com.vintile.restaurantapp.network;

import com.vintile.restaurantapp.models.RestuarantMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Sharath on 2020/02/14
 **/
public interface MainApi {

    @GET("tg1v2")
    Call<List<RestuarantMenu>> getRestuarantMenu();
}
