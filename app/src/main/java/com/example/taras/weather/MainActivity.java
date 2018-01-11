package com.example.taras.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taras.weather.repository.RepositoryController;
import com.example.taras.weather.repository.RepositoryUtils;
import com.example.taras.weather.repository.local.LocalDataUtils;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;
import com.example.taras.weather.repository.remote.OpenWeatherMapAPI;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.Fragments.CityFragment.CityFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CityFragment.OnListFragmentInteractionListener,
        RepositoryController.Callback{
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private CityFragment cityFragment;
    private static List<Repo> listTodayAll = new ArrayList<>();

    private RepositoryController repositoryController;


    public void setListTodayAll(List<Repo> listTodayAll) {
        this.listTodayAll = listTodayAll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        cityFragment = new CityFragment();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        repositoryController = new RepositoryController();
        repositoryController.registerCallback(this);
        initCityFragmet();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
     void start(){


    }

    void initCityFragmet(){
        repositoryController.getTodayForecastAllCities(this);
    }

    private void init() {
        String API_key = "caf674814c89747f40b8870b3091b074";
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.openweathermap.org")
                .build();
        OpenWeatherMapAPI openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI.class);
        Observable<OWMResponse> getForecast = openWeatherMapAPI.getWeather("703448","metric","ru", API_key);

        getForecast
                .map(OWMResponse -> RepositoryUtils.oWMresponseToRepo(OWMResponse))
                .subscribeOn(Schedulers.io())
                .subscribe(repoList -> {
                    LocalDataUtils.updateFiveDayDB(getApplicationContext(), repoList);
                    initCityFragmet();});

//        .subscribe(ItemForecast ->
//                        Controller.addUpdateData(getApplicationContext(), ItemForecast));

//        call.enqueue(new Callback<OWMResponse> () {
//            @Override
//            public void onResponse(Call<OWMResponse>  call, Response<OWMResponse>  response) {
//                if (response.isSuccessful()){
//                  Controller.addUpdateData(getApplicationContext(), Controller.responseToItemForecast(response.body()));
//                    Log.d("TAG", "Пробую добавить в базу");
//                }else Log.d("TAG", "Не удачный response");
//            }
//
//            @Override
//            public void onFailure(Call<OWMResponse>  call, Throwable t) {
//                Log.d("TAG", "Не удачно " + t.getMessage());
//            }
//        });

    }
   void saveSattings(Map<String, String> map){
        SharedPreferences sharedPreferences= getSharedPreferences("SettingsWeather", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> keys = map.keySet();
        for(String k : keys) {
            editor.putString(k, map.get(k));
            Log.d("TAG", k + map.get(k));
        }
        editor.commit();
    }

    String getSettings(String key){
        SharedPreferences  sharedPreferences = getSharedPreferences("SettingsWeather", MODE_PRIVATE);
        String tmp = sharedPreferences.getString(key, "");
        return tmp;
    }
    void removeSettings(Map<String, String> map){
        SharedPreferences sharedPreferences = getSharedPreferences("SettingsWeather", MODE_PRIVATE);
    }
    @Override
    public void onListFragmentInteraction(Repo item) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//        intent.putExtra("city_id", item.getCityID());
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void callBackForecastTodayAllCities(List<Repo> list) {
        listTodayAll = list;
        Log.d("TAG", "Колбек");
        onStartCityFragmet();
    }

    private void onStartCityFragmet(){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, cityFragment, CityFragment.TAG);
        transaction.commit();
        Log.d("TAG", "Запущено");
    }
    public static List<Repo> getListTodayAllCityes(){
        return listTodayAll;
    }
}
