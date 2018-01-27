package com.example.taras.weather.repository.api;



import com.example.taras.weather.repository.modelsResponse.FindCities.FindCitiesResponse;
import com.example.taras.weather.repository.modelsResponse.FiveDayEveryThreeHourForecast.OWMResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface OpenWeatherMapAPI {

    String API_key = "caf674814c89747f40b8870b3091b074";

    @GET("/data/2.5/forecast")
    Observable<OWMResponse> getWeather(@Query("id") String cityID,
                                       @Query("units") String units,
                                       @Query("lang") String lang,
                                       @Query("appid") String apiKey
                                    );
    @GET("/data/2.5/forecast")
    Observable<OWMResponse> getWeatherr(@QueryMap(encoded=true) Map<String, String> map);
//    "api.openweathermap.org/data/2.5/find?q=London&type=like"
    @GET("/data/2.5/find")
    Observable<FindCitiesResponse> findCity(@QueryMap(encoded=true) Map<String, String> map);
}
