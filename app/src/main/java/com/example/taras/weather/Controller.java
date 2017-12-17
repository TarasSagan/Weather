package com.example.taras.weather;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taras.weather.DB.DBHelper;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;
import com.example.taras.weather.repository.modelsForecast.FiveDayEveryThreeHourForecast.OWMResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public  class  Controller {


    public synchronized static List<ItemForecast> sortByForecastDate(List<ItemForecast> list){
        Collections.sort(list, new Comparator<ItemForecast>() {
            @Override
            public int compare(ItemForecast itemForecast, ItemForecast t1) {
                return Long.compare(itemForecast.getForecastDate(), t1.getForecastDate());
            }
        });
        return list;
    }
    public synchronized static List<ItemForecast> getForecastSelectedCity(Context context, long cityID){
        List<ItemForecast> list = new ArrayList<>();
        Date date = new Date();
        long todayDate = date.getTime();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database= dbHelper.getWritableDatabase();
        Cursor cursor  = database.query("OpenWeatherMapTable", null,
                "city_id = ?",  new String[]{Long.toString(cityID)}, null, null, "date");
        if (cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex("id");
            int lastUpdateColIndex = cursor.getColumnIndex("lastupdate");
            int cityNameColIndex = cursor.getColumnIndex("city_name");
            int dateColIndex = cursor.getColumnIndex("date");
            int cityIdColIndex = cursor.getColumnIndex("city_id");
            int temperatureColIndex = cursor.getColumnIndex("temperature");
            int descriptionColIndex = cursor.getColumnIndex("description");
            int humidityColIndex = cursor.getColumnIndex("humidity");
            int pressureColIndex = cursor.getColumnIndex("pressure");
            int windSpeedColIndex = cursor.getColumnIndex("wind_speed");
            int windDirectionColIndex = cursor.getColumnIndex("wind_direction");
            int cloudsColIndex = cursor.getColumnIndex("clouds");
            do {
                int idDB = cursor.getInt(idColIndex);
                long lastUpdateDB = cursor.getLong(lastUpdateColIndex);
                String cityNameDB = cursor.getString(cityNameColIndex);
                long cityIdDB = cursor.getLong(cityIdColIndex);
                long forecastDateDB = cursor.getLong(dateColIndex);
                double temperatureDB = cursor.getDouble(temperatureColIndex);
                String descriptionDB = cursor.getString(descriptionColIndex);
                long humidityDB = cursor.getLong(humidityColIndex);
                double pressureDB = cursor.getDouble(pressureColIndex);
                double windSpeedDB = cursor.getDouble(windSpeedColIndex);
                double windDirectionDB = cursor.getDouble(windDirectionColIndex);
                long cloudsDB = cursor.getLong(cloudsColIndex);
                if(forecastDateDB >= todayDate){
                    list.add(new ItemForecast(idDB, lastUpdateDB, cityNameDB, cityIdDB,
                            forecastDateDB, temperatureDB, descriptionDB, humidityDB, pressureDB,
                            windSpeedDB, windDirectionDB, cloudsDB));
                }
            }while (cursor.moveToNext());
        }cursor.close();
        database.close();
        list = Controller.sortByForecastDate(list);
        return list;
    }
//
//     public synchronized static List<ItemForecast> getTodayForecastAllCities(Context context){
//         Date date = new Date();
//         long todayDate = date.getTime();
//         Map<String, ItemForecast> map = new HashMap<>();
//         DBHelper dbHelper = new DBHelper(context);
//         SQLiteDatabase database= dbHelper.getWritableDatabase();
//         Cursor cursor  = database.query("OpenWeatherMapTable", null,
//                 "date >= ?",  new String[]{Long.toString(todayDate)}, null, null, "city_id");
//         if (cursor.moveToFirst()){
//             int idColIndex = cursor.getColumnIndex("id");
//             int lastUpdateColIndex = cursor.getColumnIndex("lastupdate");
//             int cityNameColIndex = cursor.getColumnIndex("city_name");
//             int dateColIndex = cursor.getColumnIndex("date");
//             int cityIdColIndex = cursor.getColumnIndex("city_id");
//             int temperatureColIndex = cursor.getColumnIndex("temperature");
//             int descriptionColIndex = cursor.getColumnIndex("description");
//             int humidityColIndex = cursor.getColumnIndex("humidity");
//             int pressureColIndex = cursor.getColumnIndex("pressure");
//             int windSpeedColIndex = cursor.getColumnIndex("wind_speed");
//             int windDirectionColIndex = cursor.getColumnIndex("wind_direction");
//             int cloudsColIndex = cursor.getColumnIndex("clouds");
//             do {
//                 int idDB = cursor.getInt(idColIndex);
//                 long lastUpdateDB = cursor.getLong(lastUpdateColIndex);
//                 String cityNameDB = cursor.getString(cityNameColIndex);
//                 long cityIdDB = cursor.getLong(cityIdColIndex);
//                 long forecastDateDB = cursor.getLong(dateColIndex);
//                 double temperatureDB = cursor.getDouble(temperatureColIndex);
//                 String descriptionDB = cursor.getString(descriptionColIndex);
//                 long humidityDB = cursor.getLong(humidityColIndex);
//                 double pressureDB = cursor.getDouble(pressureColIndex);
//                 double windSpeedDB = cursor.getDouble(windSpeedColIndex);
//                 double windDirectionDB = cursor.getDouble(windDirectionColIndex);
//                 long cloudsDB = cursor.getLong(cloudsColIndex);
//                 if(map.containsKey(cityNameDB)){
//                     if(forecastDateDB <= map.get(cityNameDB).getForecastDate()){
//                         map.put(cityNameDB, new ItemForecast(idDB, lastUpdateDB, cityNameDB, cityIdDB,
//                                 forecastDateDB, temperatureDB, descriptionDB, humidityDB, pressureDB,
//                                 windSpeedDB, windDirectionDB, cloudsDB));
//                     }
//                 } else map.put(cityNameDB, new ItemForecast(idDB, lastUpdateDB, cityNameDB, cityIdDB,
//                         forecastDateDB, temperatureDB, descriptionDB, humidityDB, pressureDB,
//                         windSpeedDB, windDirectionDB, cloudsDB));
//             }while (cursor.moveToNext());
//         }cursor.close();
//         database.close();
//         List<ItemForecast> list = new ArrayList<ItemForecast>(map.values());
//         return list;
//     }

//    public synchronized static List<ItemForecast> responseToItemForecast(OWMResponse owmResponse){
//         Date date = new Date();
//         long todayDate = date.getTime();
//         List<ItemForecast> list = new ArrayList<>();
//         for(int i=0; i < owmResponse.getList().size(); i++){
//             list.add(new ItemForecast(
//                     0,
//                     todayDate,
//                     owmResponse.getCity().getName().toString(),
//                     owmResponse.getCity().getId(),
//                     owmResponse.getList().get(i).getDt() * 1000,
//                     owmResponse.getList().get(i).getMain().getTemp(),
//                     owmResponse.getList().get(i).getWeather().get(0).getDescription(),
//                     owmResponse.getList().get(i).getMain().getHumidity(),
//                     owmResponse.getList().get(i).getMain().getPressure(),
//                     owmResponse.getList().get(i).getWind().getSpeed(),
//                     owmResponse.getList().get(i).getWind().getDeg(),
//                     owmResponse.getList().get(i).getClouds().getAll()
//             ));
//             Log.d("TAG", Long.toString(list.get(i).getForecastDate()));
//         }
//         return list;
//     }
//
//    public synchronized static void addUpdateData(Context context, List<ItemForecast> itemForecasts){
//        DBHelper dbHelper = new DBHelper(context);
//        SQLiteDatabase database= dbHelper.getWritableDatabase();
//        Cursor cursor  = database.query("OpenWeatherMapTable", null,
//                 "city_name = ? ", new String[]{itemForecasts.get(1).getCityName()}, null, null, null);
//        if(cursor.getCount() == 0) {
//            cursor.close();
//            try {
//                database.beginTransaction();
//                ContentValues contentValues = new ContentValues();
//                for (int i = 0; i < itemForecasts.size(); i++) {
//                    contentValues.put("lastupdate", itemForecasts.get(i).getLastUpdate());
//                    contentValues.put("city_name", itemForecasts.get(i).getCityName());
//                    contentValues.put("city_id ", itemForecasts.get(i).getCityID());
//                    contentValues.put("date", itemForecasts.get(i).getForecastDate());
//                    contentValues.put("temperature", itemForecasts.get(i).getTemperature());
//                    contentValues.put("description", itemForecasts.get(i).getDescription());
//                    contentValues.put("humidity", itemForecasts.get(i).getHumidity());
//                    contentValues.put("pressure", itemForecasts.get(i).getPressure());
//                    contentValues.put("wind_speed", itemForecasts.get(i).getWindSpeed());
//                    contentValues.put("wind_direction", itemForecasts.get(i).getWindDirection());
//                    contentValues.put("clouds", itemForecasts.get(i).getClouds());
//                    database.insert("OpenWeatherMapTable", null, contentValues);
//                }
//                database.setTransactionSuccessful();
//            } finally {
//                database.endTransaction();
//            }
//            database.close();
//        }else Controller.updateDataDB(context, itemForecasts);
//    }

//    public synchronized static List<Repo> getTodayForecastAllCities(Context context){
//        Log.d("TAG", "Call: getTodayForecastAllCities");
//        Calendar calendar = new GregorianCalendar();
//        int today = calendar.get(Calendar.DAY_OF_YEAR);
//        Calendar tmpCalendar = new GregorianCalendar();
//        Map<Long, Repo> map = new HashMap<>();
//        List<Repo> list = new ArrayList<>();
//        Flowable<List<Repo>> todayForecast = RepoDatabase.getInstance(context).getRepoDao().getAllRepos();
//        todayForecast
//                .flatMap(repos -> {
//                    Flowable.fromIterable(repos)
//                            .filter(repo -> {
//                                Log.d("TAGSUKA", Double.toString(repo.getTemperature()));
//                    // only today forecast four all cities
//                                 tmpCalendar.setTimeInMillis(repo.getForecastDate());
//                                 if(today == tmpCalendar.get(Calendar.DAY_OF_YEAR)){
//                                     return true;
//                                 }else return false;
//                            })
//                            .filter(repo ->{                                          // one forecast with max temperature four every cities
//                                 if (map.containsKey(repo.getCityID())) {
//                                     if(repo.getTemperature() >= map.get(repo.getCityID()).getTemperature()){
//                                         map.put(repo.getCityID(), repo);
//                                     }else return false;
//                                 }else map.put(repo.getCityID(), repo);
//                                 return true;
//                            })
//                            .map(return new Flowable<List<Repo>>(map.values()))
//                    return Flowable<List<Repo>>  ;
//                })
//
//                .doOnComplete()
//                .subscribeOn(Schedulers.io())
//                .subscribe();
//        List<Repo> list = new ArrayList<>();
//
//
//        return list;
//    }
//    protected synchronized static void updateDataDB(Context context, List<ItemForecast> list){
//        String[] tmpID = new String[list.size() + 10];
//        int a = 0;
//        DBHelper dbHelper = new DBHelper(context);
//        SQLiteDatabase database= dbHelper.getWritableDatabase();
//        Cursor cursor  = database.query("OpenWeatherMapTable", null,
//                "city_name = ? ", new String[]{list.get(0).getCityName()}, null, null, null);
//        if (cursor.moveToFirst()){
//            int idColIndex = cursor.getColumnIndex("id");
//            do {
//                tmpID[a] = Integer.toString(cursor.getInt(idColIndex));
//                a++;
//            }while (cursor.moveToNext());
//         }cursor.close();
//        try {
//            database.beginTransaction();
//            ContentValues contentValues = new ContentValues();
//         for (int i = 0; i<list.size(); i++){
//             contentValues.put("lastupdate", list.get(i).getLastUpdate());
//             contentValues.put("city_name", list.get(i).getCityName());
//             contentValues.put("city_id ", list.get(i).getCityID());
//             contentValues.put("date", list.get(i).getForecastDate());
//             contentValues.put("temperature", list.get(i).getTemperature());
//             contentValues.put("description", list.get(i).getDescription());
//             contentValues.put("humidity", list.get(i).getHumidity());
//             contentValues.put("pressure", list.get(i).getPressure());
//             contentValues.put("wind_speed", list.get(i).getWindSpeed());
//             contentValues.put("wind_direction", list.get(i).getWindDirection());
//             contentValues.put("clouds", list.get(i).getClouds());
//             database.update("OpenWeatherMapTable", contentValues, "id = ?",
//                     new String[]{tmpID[i]});
//         }
//            database.setTransactionSuccessful();
//        } finally {
//            database.endTransaction();
//        }
//        database.close();
//    }
















}
