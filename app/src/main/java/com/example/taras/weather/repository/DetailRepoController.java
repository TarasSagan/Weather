package com.example.taras.weather.repository;


import android.content.Context;
import android.util.Log;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DetailRepoController {
    private Context context;
    private Calendar todayTime;
    private DetailActivityCallback detailActivityCallback;
    private RepositoryUtils repositoryUtils = new RepositoryUtils();
    private List<Repo> todayList = new ArrayList<>();
    private List<Repo> severalDays= new ArrayList<>();

    public interface DetailActivityCallback {
        void callBackForecastSelectCity(List<Repo> todayList, List<Repo> severalDays);
    }

    public DetailRepoController(Context context, DetailActivityCallback detailActivityCallback) {
        this.context = context;
        this.detailActivityCallback = detailActivityCallback;
    }

    public void getForecastByCitySelect(Long city){
        todayTime = new GregorianCalendar();
        getTodayForecastByCityID(city, todayTime.getTimeInMillis());
        getForecastByCityID(city, todayTime.getTimeInMillis());
    }
    private synchronized void checkForCallback(){

        if(!todayList.isEmpty()){
            if (!severalDays.isEmpty()){
                detailActivityCallback.callBackForecastSelectCity(todayList, severalDays);
            }
        }
    }

    private void getTodayForecastByCityID(long cityId, long todayTime){
        Single<List<Repo>> getAllByCityId = RepoDatabase.getInstance(context).getRepoDao().getAllByCityId(cityId, todayTime);
        getAllByCityId
                .map(listRepos -> repositoryUtils.filterSelectedToday(listRepos))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    todayList = list;
                    checkForCallback();
                });
    }
    private void getForecastByCityID(long cityId, long todayTime){
        Single<List<Repo>> getAllByCityId = RepoDatabase.getInstance(context).getRepoDao().getAllByCityId(cityId, todayTime);
        getAllByCityId
                .map(listRepos -> repositoryUtils.filterSelectedSeveralDays(listRepos))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    severalDays = list;
                    checkForCallback();
                });
    }
}
