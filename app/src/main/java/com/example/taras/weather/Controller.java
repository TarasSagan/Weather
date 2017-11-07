package com.example.taras.weather;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.taras.weather.API.OpenWeatherMap.OWMResponse;
import com.example.taras.weather.DB.DBHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class  Controller {

     static Map<String, ItemForecast> getTodayForecast(Context context){
         Date date = new Date();
         long todayDate = date.getTime();
         Map<String, ItemForecast> map = new HashMap<>();
         DBHelper dbHelper = new DBHelper(context);
         SQLiteDatabase database= dbHelper.getWritableDatabase();
         Cursor cursor  = database.query("OpenWeatherMapTable", null,
                 "date >= ?",  new String[]{Long.toString(todayDate)}, null, null, "city_id");
         if (cursor.moveToFirst()){
             int idColIndex = cursor.getColumnIndex("id");
             int lastupdateColIndex = cursor.getColumnIndex("lastupdate");
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
                 long lastUpdateDB = cursor.getLong(lastupdateColIndex);
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
                 if(map.containsKey(cityNameDB)){
                     if(forecastDateDB <= map.get(cityNameDB).getForecastDate()){
                         map.put(cityNameDB, new ItemForecast(idDB, lastUpdateDB, cityNameDB, cityIdDB,
                                 forecastDateDB, temperatureDB, descriptionDB, humidityDB, pressureDB,
                                 windSpeedDB, windDirectionDB, cloudsDB));
                     }
                 } else map.put(cityNameDB, new ItemForecast(idDB, lastUpdateDB, cityNameDB, cityIdDB,
                         forecastDateDB, temperatureDB, descriptionDB, humidityDB, pressureDB,
                         windSpeedDB, windDirectionDB, cloudsDB));
             }while (cursor.moveToNext());
         }cursor.close();
         database.close();
         return map;
     }

     static List<ItemForecast> responseToItemForecast(OWMResponse owmResponse){
         Date date = new Date();
         long todayDate = date.getTime();
         List<ItemForecast> list = new ArrayList<>();
         for(int i=0; i < owmResponse.getList().size(); i++){
             list.add(new ItemForecast(
                     0,
                     todayDate,
                     owmResponse.getCity().getName().toString(),
                     owmResponse.getCity().getId(),
                     owmResponse.getList().get(i).getDt(),
                     owmResponse.getList().get(i).getMain().getTemp(),
                     owmResponse.getList().get(i).getWeather().get(0).getDescription(),
                     owmResponse.getList().get(i).getMain().getHumidity(),
                     owmResponse.getList().get(i).getMain().getPressure(),
                     owmResponse.getList().get(i).getWind().getSpeed(),
                     owmResponse.getList().get(i).getWind().getDeg(),
                     owmResponse.getList().get(i).getClouds().getAll()
             ));
         }
         return list;
     }

    static void addUpdateData(Context context, List<ItemForecast> itemForecasts){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database= dbHelper.getWritableDatabase();
        Cursor cursor  = database.query("OpenWeatherMapTable", null,
                 "city_name = ? ", new String[]{itemForecasts.get(1).getCityName()}, null, null, null);
        if(cursor.getCount() == 0) {
            cursor.close();
            try {
                database.beginTransaction();
                ContentValues contentValues = new ContentValues();
                for (int i = 0; i < itemForecasts.size(); i++) {
                    contentValues.put("lastupdate", itemForecasts.get(i).getLastUpdate());
                    contentValues.put("city_name", itemForecasts.get(i).getCityName());
                    contentValues.put("city_id ", itemForecasts.get(i).getCityID());
                    contentValues.put("date", itemForecasts.get(i).getForecastDate() * 1000);
                    contentValues.put("temperature", itemForecasts.get(i).getTemperature());
                    contentValues.put("description", itemForecasts.get(i).getDescription());
                    contentValues.put("humidity", itemForecasts.get(i).getHumidity());
                    contentValues.put("pressure", itemForecasts.get(i).getPressure());
                    contentValues.put("wind_speed", itemForecasts.get(i).getWindSpeed());
                    contentValues.put("wind_direction", itemForecasts.get(i).getWindDirection());
                    contentValues.put("clouds", itemForecasts.get(i).getClouds());
                    database.insert("OpenWeatherMapTable", null, contentValues);
                }
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
            database.close();
        }else Controller.updateDataDB(context, itemForecasts);
    }
    static void updateDataDB(Context context, List<ItemForecast> list){
        String[] tmpID = new String[list.size() + 10];
        int a = 0;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database= dbHelper.getWritableDatabase();
        Cursor cursor  = database.query("OpenWeatherMapTable", null,
                "city_name = ? ", new String[]{list.get(0).getCityName()}, null, null, null);
        if (cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex("id");
            do {
                tmpID[a] = Integer.toString(cursor.getInt(idColIndex));
                a++;
            }while (cursor.moveToNext());
         }cursor.close();
        try {
            database.beginTransaction();
            ContentValues contentValues = new ContentValues();
         for (int i = 0; i<list.size(); i++){
             contentValues.put("lastupdate", list.get(i).getLastUpdate());
             contentValues.put("city_name", list.get(i).getCityName());
             contentValues.put("city_id ", list.get(i).getCityID());
             contentValues.put("date", list.get(i).getForecastDate());
             contentValues.put("temperature", list.get(i).getTemperature());
             contentValues.put("description", list.get(i).getDescription());
             contentValues.put("humidity", list.get(i).getHumidity());
             contentValues.put("pressure", list.get(i).getPressure());
             contentValues.put("wind_speed", list.get(i).getWindSpeed());
             contentValues.put("wind_direction", list.get(i).getWindDirection());
             contentValues.put("clouds", list.get(i).getClouds());
             database.update("OpenWeatherMapTable", contentValues, "id = ?",
                     new String[]{tmpID[i]});
         }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        database.close();
    }
}
