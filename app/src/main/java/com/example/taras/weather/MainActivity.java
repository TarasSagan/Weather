package com.example.taras.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.taras.weather.fragments.AddCityFragment.AddCityFragment;
import com.example.taras.weather.fragments.AddCityFragment.ListCitiesFragment.ListCitiesFragment;
import com.example.taras.weather.repository.MainRepoController;
import com.example.taras.weather.repository.api.ClientRetofit;
import com.example.taras.weather.repository.api.OpenWeatherMapAPI;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.fragments.CityFragment.CityFragment;
import com.example.taras.weather.repository.modelsResponse.FindCities.FindCitiesResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CityFragment.OnListFragmentInteractionListener,
        MainRepoController.MainActivityCallback ,ListCitiesFragment.OnListFragmentInteractionListener{
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private CityFragment cityFragment;
    private static List<Repo> listTodayAll;
    private MainRepoController mainRepoController;
    private AddCityFragment addCityFragment;
    private FloatingActionButton fabAdd, fabSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        cityFragment = new CityFragment();
        addCityFragment = new AddCityFragment();

        fabSettings = findViewById(R.id.fabSettings);
        fabAdd = findViewById(R.id.fabAdd);
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCityFragment.show(getSupportFragmentManager(), addCityFragment.getTag());
            }
        });
//        testFindCity();

        mainRepoController = new MainRepoController(getApplicationContext(), this);
        initCityFragment();

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

    void initCityFragment(){
        mainRepoController.initController(this);
    }

//   void saveSattings(Map<String, String> map){
//        SharedPreferences sharedPreferences= getSharedPreferences("SettingsWeather", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Set<String> keys = map.keySet();
//        for(String k : keys) {
//            editor.putString(k, map.get(k));
//            Log.d("TAG", k + map.get(k));
//        }
//        editor.commit();
//    }
//
//    String getSettings(String key){
//        SharedPreferences  sharedPreferences = getSharedPreferences("SettingsWeather", MODE_PRIVATE);
//        String tmp = sharedPreferences.getString(key, "");
//        return tmp;
//    }
//    void removeSettings(Map<String, String> map){
//        SharedPreferences sharedPreferences = getSharedPreferences("SettingsWeather", MODE_PRIVATE);
//    }
    @Override
    public void onListFragmentInteraction(Repo item) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("city_id", item.getCityID());
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void callBackForecastTodayAllCities(List<Repo> list) {
        listTodayAll = list;
        onStartCityFragment();
    }


    private void onStartCityFragment(){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, cityFragment, CityFragment.TAG);
        transaction.commit();
    }
    public static List<Repo> getListTodayAllCities(){
        return listTodayAll;
    }
    void testFindCity(){
        OpenWeatherMapAPI openWeatherMapAPI = new ClientRetofit().getClient();
        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("appid", "6e104a1a0c242bb277980d2e9ecceeae");
        tmpMap.put("lang", "ru");
        tmpMap.put("units", "metric");
        tmpMap.put("q", "Kharkiv");
        tmpMap.put("type", "like");
        openWeatherMapAPI.findCity(tmpMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> Log.d("TAG", list.getList().get(0).getName()));

    }

    @Override
    public void onListFragmentInteraction(FindCitiesResponse findCitiesResponseItem) {

    }
}
