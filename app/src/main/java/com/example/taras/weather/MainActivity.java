package com.example.taras.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.taras.weather.fragments.AddCityFragment.AddCityFragment;
import com.example.taras.weather.fragments.AddCityFragment.ModelCitySearch;
import com.example.taras.weather.fragments.SettingsFragment.SettingsFragment;
import com.example.taras.weather.repository.MainRepoController;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.fragments.CityFragment.CityFragment;
import com.example.taras.weather.settings.SettingsController;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements
        CityFragment.OnListFragmentInteractionListener,
        MainRepoController.MainActivityCallback,
        AddCityFragment.AddCityListListener{
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private CityFragment cityFragment;
    private static List<Repo> listTodayAll;
    private MainRepoController mainRepoController;
    private AddCityFragment addCityFragment;
    private SettingsFragment settingsFragment;
    private Toolbar toolbar;


    private static SettingsController settingsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listTodayAll = new ArrayList<>();
        manager = getSupportFragmentManager();
        addCityFragment = new AddCityFragment();
        settingsFragment = new SettingsFragment();
        settingsController = new SettingsController();
        settingsController.initSettingsController(this);
        mainRepoController = new MainRepoController(getApplicationContext(), this);
        initApp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update) {
            List<Long> list = new ArrayList<>();
            for (Repo city : listTodayAll){
                list.add(city.getCityID());
            }
            mainRepoController.forceUpdate();
            return true;
        }else if (id == R.id.action_add) {
            toolbar.setTitle("Add city");
            transaction = manager.beginTransaction();
            transaction.replace(R.id.containerMainActivity, addCityFragment, AddCityFragment.TAG);
            transaction.commit();
            return true;
        }else if (id == R.id.action_settings) {
            toolbar.setTitle("Settings");
            transaction = manager.beginTransaction();
            transaction.replace(R.id.containerMainActivity, settingsFragment, SettingsFragment.TAG);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    void initApp(){
        mainRepoController.initController(this);
    }

    @Override
    public void onClickCallbackCityFragment(Repo item) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("city_id", item.getCityID());
        startActivity(intent);

    }

    @Override
    public void onDeleteCallbackCityFragment(Repo city) {
        mainRepoController.deleteCity(city);
    }

    @Override
    public void onShowForecast(List<Repo> list) {
        listTodayAll = list ;
        onStartCityFragment();
    }

    @Override
    public void onBackPressed() {
        onStartCityFragment();
    }

    @Override
    public void onShowInfo(String info) {
        Snackbar.make(this.findViewById(R.id.containerMainActivity), info, Snackbar.LENGTH_LONG).show();
    }

    private void onStartCityFragment(){
        toolbar.setTitle("");
        cityFragment = new CityFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.containerMainActivity, cityFragment, CityFragment.TAG);
        transaction.commit();
    }

    public static List<Repo> getListTodayAllCities(){
        return listTodayAll;
    }


    @Override
    public void addCityCallback(ModelCitySearch searchCitiesResponseItem) {
        List<Long> list = new ArrayList<>();
        list.add(searchCitiesResponseItem.getCityID());
        mainRepoController.addCity(getApplicationContext(), list);
    }
    public static SettingsController getSettingsController() {
        return settingsController;
    }
}
