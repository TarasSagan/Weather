package com.example.taras.weather.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Taras on 28.10.2017.
 */

public class OpenWeatherMapDBHelper extends SQLiteOpenHelper {
    public OpenWeatherMapDBHelper(Context context) {
        super(context, "WeatherDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE OpenWeatherMapTable ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,  "
                    + "lastupdate TEXT, "
                    + "city_name TEXT, "
                    + "city_id INTEGER, "
                    + "temperature_value TEXT, "
                    + "temperature_min TEXT, "
                    + "temperature_max TEXT, "
                    + "temperature_units TEXT, " //Find method convert!!!
                    + "description TEXT, "
                    + "description_lang TEXT"
                    + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
