package com.example.taras.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.taras.weather.repository.MainRepoController;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.Fragments.CityFragment.CityFragment;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CityFragment.OnListFragmentInteractionListener,
        MainRepoController.MainActivityCallback {
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private CityFragment cityFragment;
    private static List<Repo> listTodayAll;
    private MainRepoController mainRepoController;


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
}
