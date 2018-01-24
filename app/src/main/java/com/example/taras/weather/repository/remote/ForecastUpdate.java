package com.example.taras.weather.repository.remote;

import android.content.Context;
import com.example.taras.weather.repository.RepositoryUtils;
import com.example.taras.weather.repository.local.LocalDataUtils;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class ForecastUpdate{

    private ForecastUpdateCallback forecastUpdateCallback;
    private String API_key = "6e104a1a0c242bb277980d2e9ecceeae";
    private Context context;
    private List<Observable<OWMResponse>> listObservable;
    private List<Long> listCities;
    private List<Map<String, String>> listQueryMap;
    private OpenWeatherMapAPI openWeatherMapAPI;
    private int iteratorOfUpdate = 0;

    public ForecastUpdate(ForecastUpdateCallback forecastUpdateCallback, Context context, List<Long> listCities) {
        this.forecastUpdateCallback = forecastUpdateCallback;
        this.context = context;
        this.listCities = listCities;
    }
    public void updateData(){
        prepareListQueryMaps(listCities);
        prepareListObservales(listQueryMap);
        updateFromListObservable(listObservable, context);

    }
    private void prepareListObservales(List<Map<String, String>> listQueryMap){
        listObservable = new ArrayList<>();
        for (int i=0; i<listQueryMap.size(); i++){
            listObservable.add(openWeatherMapAPI.getWeatherr(listQueryMap.get(i)));
        }
    }
    private void prepareListQueryMaps(List<Long> listCities){
        openWeatherMapAPI = new ClientRetofit().getClient();
        listQueryMap = new LinkedList<>();
        Map<String, String> tmpMap;
        for (int i=0; i<listCities.size(); i++){
            tmpMap = new HashMap<>();
            tmpMap.put("appid", API_key);
            tmpMap.put("lang", "ru");
            tmpMap.put("units", "metric");
            tmpMap.put("id", Long.toString(listCities.get(i)));
            listQueryMap.add(tmpMap);
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
        void updateError(String error);
    }
}
