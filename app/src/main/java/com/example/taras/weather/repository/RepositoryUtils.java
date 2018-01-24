package com.example.taras.weather.repository;

import android.util.Log;

import com.example.taras.weather.ItemForecast;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Repo> filterTodayAllCities(List<Repo> list) {
        double tempInMap, tempNew;
        Calendar calendar = new GregorianCalendar();
        Calendar calendarOfNewForecast = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        Map<Long, Repo> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            calendarOfNewForecast.setTimeInMillis(list.get(i).getForecastDate());
            int newForecastDay = calendarOfNewForecast.get(Calendar.DAY_OF_YEAR);

            if (today == newForecastDay) {                                  //if new forecast is today forecast
                if (map.containsKey(list.get(i).getCityID())) {             //if new forecast by target city is in my map
                    tempInMap = map.get(list.get(i).getCityID()).getTemperature();
                    tempNew = list.get(i).getTemperature();
                    if (tempNew >= tempInMap) {                              //if new temperature is >= temperature in my map
                        map.put(list.get(i).getCityID(), list.get(i));
                    }
                } else map.put(list.get(i).getCityID(), list.get(i));
            }
        }
        return new ArrayList<>(map.values());
    }
    public    List<Repo> filterSelectedSeveralDays(List<Repo> list){
        Calendar newCalendar = new GregorianCalendar();
        Map<Integer, Repo> map = new HashMap<>();
        for(Repo repo : list ){
            newCalendar.setTimeInMillis(repo.getForecastDate());
            int newDay = newCalendar.get(Calendar.DAY_OF_YEAR);
            if(map.containsKey(newDay)){
                double newTemp = repo.getTemperature();
                double oldTemp = map.get(newDay).getTemperature();
                if(newTemp > oldTemp){
                    map.remove(newDay);
                    map.put(newDay, repo);
                }
            }else{
                map.put(newDay, repo);
            }
        }
        List<Repo> listForReturn = new ArrayList<>(map.values());
        sortByForecastDate(listForReturn);
        return listForReturn;
    }

    public List<Repo> filterSelectedToday(List<Repo> listSelectedCity){
        Calendar nowCalendar = new GregorianCalendar();
        Calendar tempCalendar = new GregorianCalendar();

        List<Repo> list = new ArrayList<>();
        for (Repo repo : listSelectedCity){
            tempCalendar.setTime(new Date(repo.getForecastDate()));
            if(nowCalendar.getTimeInMillis() <= tempCalendar.getTimeInMillis()){
                if(nowCalendar.get(Calendar.DAY_OF_YEAR) == tempCalendar.get(Calendar.DAY_OF_YEAR)){
                    list.add(repo);
                }
            }
        }
        sortByForecastDate(list);
        return list;
    }
    private synchronized   List<Repo> sortByForecastDate(List<Repo> list){
        Collections.sort(list, new Comparator<Repo>() {
            @Override
            public int compare(Repo o1, Repo o2) {
                return Long.compare(o1.getForecastDate(), o2.getForecastDate());
            }
        });
        return list;
    }
}
