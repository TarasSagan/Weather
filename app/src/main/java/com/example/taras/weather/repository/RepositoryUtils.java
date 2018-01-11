package com.example.taras.weather.repository;

import android.util.Log;

import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepositoryUtils {

    public synchronized static List<Repo> oWMresponseToRepo(OWMResponse owmResponse){
        Date date = new Date();
        long todayDate = date.getTime();
        List<Repo> list = new ArrayList<>();
        for(int i=0; i < owmResponse.getList().size(); i++){
            list.add(new Repo(
                    (owmResponse.getCity().getId() * 100) + i ,
                    todayDate,
                    owmResponse.getCity().getName().toString(),
                    owmResponse.getCity().getId(),
                    owmResponse.getList().get(i).getDt() * 1000,
                    owmResponse.getList().get(i).getMain().getTemp(),
                    owmResponse.getList().get(i).getWeather().get(0).getDescription(),
                    owmResponse.getList().get(i).getMain().getHumidity(),
                    owmResponse.getList().get(i).getMain().getPressure(),
                    owmResponse.getList().get(i).getWind().getSpeed(),
                    owmResponse.getList().get(i).getWind().getDeg(),
                    owmResponse.getList().get(i).getClouds().getAll()
            ));
            Log.d("TAG", Long.toString(list.get(i).getForecastDate()));
        }
        return list;
    }
}
