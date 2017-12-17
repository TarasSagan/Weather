package com.example.taras.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
        SeveralDayForecastFragment.OnListFragmentInteractionListener{
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private TodayForecastFragment todayForecastFragment;
    private SeveralDayForecastFragment severalDayForecastFragment;
    private static List<Repo> listSelectedCity = new ArrayList<>();


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        todayForecastFragment = new TodayForecastFragment();
        severalDayForecastFragment = new SeveralDayForecastFragment();
        manager = getSupportFragmentManager();

//        listSelectedCity = Controller.getForecastSelectedCity(getApplicationContext(),
//                getIntent().getLongExtra("city_id", 00));

        transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(TodayForecastFragment.TAG) == null) {
            transaction.replace(R.id.DetailActivityContainer, todayForecastFragment, TodayForecastFragment.TAG);
            transaction.commit();
        }

    }
    public synchronized static List<Repo> getListSeveralDays(){
        Calendar newCalendar = new GregorianCalendar();
        Map<Integer, Repo> map = new HashMap<>();
        for(int i = 0; i < listSelectedCity.size(); i++ ){
            newCalendar.setTimeInMillis(listSelectedCity.get(i).getForecastDate());
            int newDay = newCalendar.get(Calendar.DAY_OF_YEAR);
            if(map.containsKey(newDay)){
                double newTemp = listSelectedCity.get(i).getTemperature();
                double oldTemp = map.get(newDay).getTemperature();
                if(newTemp > oldTemp){
                    map.remove(newDay);
                    map.put(newDay, listSelectedCity.get(i));
                }
            }else{
                map.put(newDay, listSelectedCity.get(i));
            }
        }
        List<Repo> list = new ArrayList<Repo>(map.values());
//        list = Controller.sortByForecastDate(list);
        return list;
    }
    public static List<Repo> getListToday(){
        Calendar nowCalendar = new GregorianCalendar();
        Calendar tempCalendar = new GregorianCalendar();

        List<Repo> list = new ArrayList<>();
        for (int i = 0; i < listSelectedCity.size(); i++){
            tempCalendar.setTime(new Date(listSelectedCity.get(i).getForecastDate()));
            if(nowCalendar.getTimeInMillis() <= tempCalendar.getTimeInMillis()){
                if(nowCalendar.get(Calendar.DAY_OF_MONTH) == tempCalendar.get(Calendar.DAY_OF_MONTH)){
                    list.add(listSelectedCity.get(i));
                }
            }
        }
//        list = Controller.sortByForecastDate(list);
        return list;
    }

    @Override
    public void onListFragmentInteraction(Repo item) {

    }
}
