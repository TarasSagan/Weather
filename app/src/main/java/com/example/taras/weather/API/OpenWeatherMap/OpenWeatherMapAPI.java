package com.example.taras.weather.API.OpenWeatherMap;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface OpenWeatherMapAPI {

    String API_key = "6e104a1a0c242bb277980d2e9ecceeae";
    @POST("data/2.5/group")
    Call<OWMResponse> getWeather(@Query("id") String id,
                                 @Query("units") String units,
                                 @Query("appid") String apiKey
                                    );
}
