package com.example.taras.weather;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.taras.weather.API.OpenWeatherMap.OWMResponse;
import com.example.taras.weather.DB.OpenWeatherMapDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras on 28.10.2017.
 */

public  class  Controller {


//     static void addOpenWeaherMapToDB(Context context, OWMResponse owmResponse){
//        OpenWeatherMapDBHelper dbHelper = new OpenWeatherMapDBHelper(context);
//        SQLiteDatabase database= dbHelper.getWritableDatabase();
//         try {
//             database.beginTransaction();
//             ContentValues contentValues = new ContentValues();
//             contentValues.put("lastupdate", lastupdate);
//             contentValues.put("city_name", cityName);
//             contentValues.put("city_id ", cityId );
//             contentValues.put("temperature_value", temperatureValue);
//             contentValues.put("temperature_min", temperatureMin);
//             contentValues.put("temperature_max", temperatureMax);
//             contentValues.put("temperature_units", temperatureUnits);
//             contentValues.put("description", description);
//             contentValues.put("description_lang", descriptionLang);
//             database.insert("OpenWeatherMapTable", null, contentValues);
//             database.setTransactionSuccessful();
//         } finally {
//             database.endTransaction();
//         }
//         database.close();
//    }

}
