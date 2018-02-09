package com.example.taras.weather.repository;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import com.example.taras.weather.repository.api.ForecastUpdate;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
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
    private long UPDATE_INTERVAL = (60 * 60 * 1000);

    public void initController(Context context) {
        this.context = context;
        getTodayForecastAllCities();

    }

    private void continueInit(List<Repo> list) {
        mainActivityCallback.onShowForecast(list);
        checkForUpdate();
    }
    private void updateData(List<Long> listCities){
        if (isNetworkConnected()) {
            Log.d("TAF", Integer.toString(listCities.size()));
            forecastUpdate = new ForecastUpdate(this, context, listCities);
            forecastUpdate.updateData();
        }else mainActivityCallback.onShowInfo("No internet connection");
    }
    public void forceUpdate(){
        Single<List<Repo>> getAllRepos = RepoDatabase.getInstance(context).getRepoDao().getAll();
        getAllRepos
                .map(listRepos -> repositoryUtils.createListToForceUpdt(listRepos)) // This method also write data in Map: itemsForUpdt
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(longsList -> updateData(longsList));
    }

    public void deleteCity(Repo city){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                RepoDatabase.getInstance(context).getRepoDao().delete(city.getCityID());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> initController(context));

    }

    public void addCity(Context context, List<Long> city){
        this.context = context;
        forecastUpdate = new ForecastUpdate(this, context, city);
        forecastUpdate.updateData();
    }

    private void checkForUpdate(){
        Single<List<Repo>> getAllRepos = RepoDatabase.getInstance(context).getRepoDao().getAll();
        getAllRepos
                .map(listRepos -> repositoryUtils.checkForUpdt(listRepos, UPDATE_INTERVAL)) // This method also write data in Map: itemsForUpdt
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

    @Override
    public void updateSuccessfully() {
        getTodayForecastAllCities();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }


    public interface MainActivityCallback {
        void onShowForecast(List<Repo> list);
        void onShowInfo(String info);
    }
}
