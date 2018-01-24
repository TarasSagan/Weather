package com.example.taras.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.taras.weather.repository.DetailRepoController;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.Fragments.SeveralDayForecastFragment.SeveralDayForecastFragment;
import com.example.taras.weather.Fragments.TodayForecastFragment.TodayForecastFragment;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailActivity extends AppCompatActivity implements TodayForecastFragment.OnListFragmentInteractionListener,
        SeveralDayForecastFragment.OnListFragmentInteractionListener, DetailRepoController.DetailActivityCallback{
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private TodayForecastFragment todayForecastFragment;
    private SeveralDayForecastFragment severalDayForecastFragment;
    private DetailRepoController detailRepoController;
    private static List<Repo> listToday = new ArrayList<>();
    private static List<Repo> listSeveralDays = new ArrayList<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_today:
                    transaction = manager.beginTransaction();
                    if (manager.findFragmentByTag(TodayForecastFragment.TAG) == null) {
                        transaction.replace(R.id.DetailActivityContainer, todayForecastFragment, TodayForecastFragment.TAG);
                        transaction.commit();
                    }
                    return true;
                case R.id.navigation_several_day:

                    transaction = manager.beginTransaction();
                    if (manager.findFragmentByTag(SeveralDayForecastFragment.TAG) == null) {
                        transaction.replace(R.id.DetailActivityContainer, severalDayForecastFragment, SeveralDayForecastFragment.TAG);
                        transaction.commit();
                    }
                    return true;

            }
            return false;
        }
    };

    public static List<Repo> getListToday() {
        return listToday;
    }

    public static List<Repo> getListSeveralDays() {
        return listSeveralDays;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        todayForecastFragment = new TodayForecastFragment();
        severalDayForecastFragment = new SeveralDayForecastFragment();
        manager = getSupportFragmentManager();

        startInit();

    }
    private void startInit(){
        detailRepoController = new DetailRepoController(getApplicationContext(), this);
        detailRepoController.getForecastByCitySelect( getIntent().getLongExtra("city_id", 00));
    }
    private void continueInit(){
        transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(TodayForecastFragment.TAG) == null) {
            transaction.replace(R.id.DetailActivityContainer, todayForecastFragment, TodayForecastFragment.TAG);
            transaction.commit();
        }
    }


    @Override
    public void onListFragmentInteraction(Repo item) {

    }


    @Override
    public void callBackForecastSelectCity(List<Repo> todayList, List<Repo> severalDays) {

        Log.d("todayList.size = " , Integer.toString(todayList.size()));
        Log.d("severalDays.size = " , Integer.toString(severalDays.size()));
        Log.d("TAG" , "TAAAAAAAAAAAAAAAAAAAAAAAGGGGGGGGGGGGG");
        listToday = todayList;
        listSeveralDays = severalDays;
        continueInit();
    }
}
