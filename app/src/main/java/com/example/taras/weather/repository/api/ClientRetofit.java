package com.example.taras.weather.repository.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Taras on 14.12.2017.
 */

public class ClientRetofit {
    private OpenWeatherMapAPI openWeatherMapAPI;
    public OpenWeatherMapAPI getClient(){
        if(openWeatherMapAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.openweathermap.org")
                    .build();
            return openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI.class);
        }else return openWeatherMapAPI;
    }
}
