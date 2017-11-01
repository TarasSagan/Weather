package com.example.taras.weather.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Taras on 28.10.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "WeatherDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE OpenWeatherMapTable ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,  "
                    + "lastupdate TEXT, "
                    + "date TEXT, "
                    + "city_name TEXT, "
                    + "city_id INTEGER, "
                    + "temperature TEXT, "
                    + "description TEXT, "
                    + "humidity TEXT, "
                    + "pressure TEXT, "
                    + "wind_speed TEXT, "
                    + "wind_direction TEXT, "
                    + "clouds TEXT "
                    + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
