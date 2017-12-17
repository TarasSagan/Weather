package com.example.taras.weather.repository;
import android.content.Context;
import android.util.Log;

import com.example.taras.weather.repository.local.LocalDataUtils;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;
import com.example.taras.weather.repository.remote.ClientRetofit;
import com.example.taras.weather.repository.remote.OpenWeatherMapAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepositoryController {
    private  Map<Long, Repo> itemsForIpdt = new HashMap<>();
//    private List<Repo> itemsForIpdt ;
    private OpenWeatherMapAPI openWeatherMapAPI = new ClientRetofit().getClient();
    private  volatile int iteratorUpdt = 0;

    public interface Callback {
        void callBackForecastTodayAllCities(List<Repo> list);
    }

    Callback callback;

    public void registerCallback(Callback callback) {
        this.callback = callback;
    }

    void doSomething(List<Repo> list) {
        callback.callBackForecastTodayAllCities(list);

    }

    public void getTodayForecastAllCities(Context context) {
        List<Repo> todayForecastAllCityes = new ArrayList<>();
        List<Repo> list = new ArrayList<>();
        List<Repo> tmpList = new ArrayList<>();
        Map<Long, Repo> map = new HashMap<>();
        Flowable<Map<Long, Repo>> flowableMap;
        Flowable<List<Repo>> getAllRepos = RepoDatabase.getInstance(context).getRepoDao().getAll();
        getAllRepos
                .flatMap(listRepos -> Flowable.just(filterTodayAll(listRepos)))
//                .filter(repo -> {
//                    if (map.containsKey(repo.getCityID())) {
//                        return true;
//                    }else {
//                        map.put(repo.getCityID(), repo);
//                        return false;
//                    }
//                })
//                .filter(repo -> {
//                    double tempInMap = map.get(repo.getCityID()).getTemperature();
//                    double tempNew = repo.getTemperature();
//                    if(tempNew >= tempInMap){
//                        map.put(repo.getCityID(), repo);
//                        return false;
//                    }else return false;
//                })
//                .filter(repo -> )
                .subscribeOn(Schedulers.io())
                .subscribe(listRepo -> {
                    doSomething(new ArrayList<>(listRepo));
                    updateAllDataInDB(context);
                });


    }

    private synchronized List<Repo> filterTodayAll(List<Repo> list) {
        double tempInMap, tempNew;
        Calendar calendar = new GregorianCalendar();
        Calendar calendarOfNewForecast = new GregorianCalendar();
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        Map<Long, Repo> map = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            if (!itemsForIpdt.containsKey(list.get(i).getCityID())) {     //add items four next update
                itemsForIpdt.put(list.get(i).getCityID(), list.get(i));
            }

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
//        itemsForIpdt = new ArrayList<>(itemsForIpdt.values());
        return new ArrayList<>(map.values());
    }

//    void updateAllData(Context context){
//        if(iteratorUpdt < itemsForIpdt.size()){
//            if (checkForUpdate(itemsForIpdt.get(iteratorUpdt).getLastUpdate())) {
//                updtForecastByCity(itemsForIpdt.get(iteratorUpdt), context);
//            }
//        }else {
//            iteratorUpdt = 0;
//            getTodayForecastAllCities(context);}
//    }

    private void updateAllDataInDB(Context context) {
        Log.d("TAG", " updateAllDataInDB");
        Log.d("TAG", "itemsForIpdt.values() = " + Integer.toString(itemsForIpdt.size()));
        String API_key = "6e104a1a0c242bb277980d2e9ecceeae";
        Map<String, String> queryMap = new HashMap<>();
        List<Repo> listCities = new ArrayList<>(itemsForIpdt.values());
        List<Observable<OWMResponse>> listObservable = new ArrayList<>();



        for(int i = 0; i < listCities.size(); i++) {
            if (checkForUpdate(listCities.get(i).getLastUpdate())) {

                queryMap.put("appid", API_key);
                queryMap.put("lang", "ru");
                queryMap.put("units", "metric");
                queryMap.put("id", "703448");
//                queryMap.put("id", Long.toString(itemsForIpdt.get(i).getCityID()));

                listObservable.add(openWeatherMapAPI.getWeatherr(queryMap));
            }
            Log.d("TAG", Integer.toString(listObservable.size()));
        }
        Observable.merge(listObservable)
                .doOnError(boody -> Log.d("TAG", boody.getMessage()))
                .doOnSubscribe(boody -> Log.d("TAG", "Cтрарт"))
                .map(OWMResponse -> RepositoryUtils.oWMresponseToRepo(OWMResponse))
                .subscribeOn(Schedulers.io())
                .subscribe(repoList -> {
                            LocalDataUtils.updateFiveDayDB(context, repoList);
                            Log.d("TAG", "ОБНОВЛЕНО");
                        });

//        if(checkForUpdate(listCities.get(0).getLastUpdate())) {
//            int iterationsOfListCities = 0;
//
//            queryMap.put("appid", API_key);
//            queryMap.put("lang", "ru");
//            queryMap.put("units", "metric");
//            if (listCities.size() > iterationsOfListCities) {
//                queryMap.put("id", Long.toString(listCities.get(iterationsOfListCities).getCityID()));
//                iterationsOfListCities++;
//
//                Observable<OWMResponse> getForecast = openWeatherMapAPI.getWeatherr(queryMap);
//                getForecast
//                        .map(OWMResponse -> RepositoryUtils.oWMresponseToRepo(OWMResponse))
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(repoList -> {
//                            LocalDataUtils.updateFiveDayDB(context, repoList);
//                            updateAllDataInDB(context);
//                        });
//
//
//            } else getTodayForecastAllCities(context);
//        }else getTodayForecastAllCities(context);
    }
//    private void updtForecastByCity(Repo city, Context context){
//        String API_key = "caf674814c89747f40b8870b3091b074";
//        Map<String, String> queryMap = new HashMap<>();
//        queryMap.put("appid", API_key);
//        queryMap.put("lang", "ru");
//        queryMap.put("units", "metric");
//        queryMap.put("id", Long.toString(city.getCityID()));
//
//        Observable<OWMResponse> getForecast = openWeatherMapAPI.getWeatherr(queryMap);
//        getForecast
//                .map(OWMResponse -> RepositoryUtils.oWMresponseToRepo(OWMResponse))
//                .subscribeOn(Schedulers.io())
//                .subscribe(repoList -> {
//                    LocalDataUtils.updateFiveDayDB(context, repoList);
//                    iteratorUpdt++;
//                    updateAllData(context);
//                });
//    }
    private boolean checkForUpdate(Long updtTime) {
        Calendar calendar = new GregorianCalendar();
        long hoursAgoTime = calendar.getTimeInMillis() - (60 * 60 * 1000);
        if(updtTime < hoursAgoTime){
            return true;
        }else return false;
    }
}