package com.example.taras.weather.API.OpenWeatherMap;



import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface OpenWeatherMapAPI {

    String API_key = "6e104a1a0c242bb277980d2e9ecceeae";
    @GET("/data/2.5/forecast")
    Call<OWMResponse> getWeather(@Query("id") String cityID,
//                              @Query("cnt") int days,
                              @Query("units") String units,
                              @Query("lang") String lang,
                              @Query("appid") String apiKey
                                    );
}
