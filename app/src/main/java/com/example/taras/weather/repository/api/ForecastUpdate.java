package com.example.taras.weather.repository.api;

import android.content.Context;

import com.example.taras.weather.MainActivity;
import com.example.taras.weather.repository.RepositoryUtils;
import com.example.taras.weather.repository.local.LocalDataUtils;
import com.example.taras.weather.repository.modelsResponse.FiveDayEveryThreeHourForecast.OWMResponse;
import com.example.taras.weather.settings.SettingsController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class ForecastUpdate{

    private ForecastUpdateCallback forecastUpdateCallback;
    private Context context;
    private List<Observable<OWMResponse>> listObservable;
    private List<Long> listCities;
    private List<Map<String, String>> listQueryMap;
    private OpenWeatherMapAPI openWeatherMapAPI;
    private int iteratorOfUpdate = 0;
    private static SettingsController settingsController;

    public ForecastUpdate(ForecastUpdateCallback forecastUpdateCallback, Context context, List<Long> listCities) {
        this.forecastUpdateCallback = forecastUpdateCallback;
        this.context = context;
        this.listCities = listCities;
        settingsController = MainActivity.getSettingsController();
    }
    public void updateData(){
        prepareListQueryMaps(listCities);
        prepareListObservales(listQueryMap);
        updateFromListObservable(listObservable, context);
    }

    private void prepareListQueryMaps(List<Long> listCities){
        Map<String, String> settings;
        openWeatherMapAPI = new ClientRetrofit().getClient();
        listQueryMap = new ArrayList<>();
        for (int i=0; i<listCities.size(); i++){
            settings = settingsController.getSettings();
            settings.put("id", Long.toString(listCities.get(i)));
            listQueryMap.add(settings);
        }
    }
    private void prepareListObservales(List<Map<String, String>> listQueryMap){
        listObservable = new ArrayList<>();
        for (int i=0; i<listQueryMap.size(); i++){
            listObservable.add(openWeatherMapAPI.getWeather(listQueryMap.get(i)));
        }
    }

    private void updateFromListObservable(List<Observable<OWMResponse>> listObservable, Context context ){
        Observable.concat(listObservable)
                .map(OWMResponse -> RepositoryUtils.oWMresponseToRepo(OWMResponse))
                .subscribeOn(Schedulers.io())
                .subscribe(repoList ->{
                    LocalDataUtils.updateFiveDayDB(context, repoList);
                    iteratorOfUpdate++;
                    checkListObservable();});
    }

    private void checkListObservable(){
        if (iteratorOfUpdate == listObservable.size()){
            iteratorOfUpdate = 0;
            forecastUpdateCallback.updateSuccessfully();
        }
    }

    public interface ForecastUpdateCallback {
        void updateSuccessfully();

    }
}
