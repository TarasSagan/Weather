package com.example.taras.weather.repository;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import com.example.taras.weather.repository.api.ForecastUpdate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainRepoController implements ForecastUpdate.ForecastUpdateCallback{
    public MainRepoController(Context context, MainActivityCallback mainActivityCallback) {
        this.context = context;
        this.mainActivityCallback = mainActivityCallback;
    }
    private ForecastUpdate forecastUpdate;
    private MainActivityCallback mainActivityCallback;
    private Context context;
    private RepositoryUtils repositoryUtils = new RepositoryUtils();

    @Override
    public void updateSuccessfully() {
        getTodayForecastAllCities();
    }

    @Override
    public void updateError(String error) {

    }

    public interface MainActivityCallback {
        void callBackForecastTodayAllCities(List<Repo> list);
    }

    public void initController(Context context) {
        this.context = context;
        Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();
        getTodayForecastAllCities();

    }

    private void continueInit(List<Repo> list) {
        mainActivityCallback.callBackForecastTodayAllCities(list);
        checkForUpdate();
    }

    private void updateData(List<Long> listCities){
        forecastUpdate = new ForecastUpdate(this, context, listCities);
        forecastUpdate.updateData();
    }
    private void checkForUpdate(){
        Single<List<Repo>> getAllRepos = RepoDatabase.getInstance(context).getRepoDao().getAll();
        getAllRepos
                .map(listRepos -> checkForUpdt(listRepos)) // This method also write data in Map: itemsForUpdt
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listCities -> updateData(listCities));
    }

    private void getTodayForecastAllCities() {
        Single<List<Repo>> getAllRepos = RepoDatabase.getInstance(context).getRepoDao().getAll();
        getAllRepos
                .map(listRepos -> repositoryUtils.filterTodayAllCities(listRepos)) // This method also write data in Map: itemsForUpdt
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listRepo -> continueInit(new ArrayList<>(listRepo)));
    }

    private List<Long> checkForUpdt(List<Repo> repoForUpdt){
        Calendar calendar = new GregorianCalendar();
        Map<Long, Repo> itemsMap = new HashMap<>();
        List<Repo> list;
        List<Long> listCities = new ArrayList<>();
        long hoursAgoTime = calendar.getTimeInMillis() - (60 * 60 * 1000);
        for (int i = 0; i < repoForUpdt.size(); i++) {
            long timeInRepo = repoForUpdt.get(i).getLastUpdate();
            Log.d("TAG", "hoursAgoTime  " + Long.toString(hoursAgoTime) + "\n  " + " timeInRepo " + Long.toString(timeInRepo));
            if (timeInRepo < hoursAgoTime) {
                if (!itemsMap.containsKey(repoForUpdt.get(i).getCityID())){
                    itemsMap.put(repoForUpdt.get(i).getCityID(), repoForUpdt.get(i));
                }
            }
        }
        list = new ArrayList<>(itemsMap.values());
        for (Repo repo : list){
            listCities.add(repo.getCityID());
        }
        return listCities;
    }
}
