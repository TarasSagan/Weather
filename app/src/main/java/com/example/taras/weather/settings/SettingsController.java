package com.example.taras.weather.settings;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.taras.weather.settings.values.Languages;
import com.example.taras.weather.settings.values.UnitsFormat;
import com.example.taras.weather.settings.values.models.Lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;

public class SettingsController {
    private Map<String, String> defaultSettings;
    private static Context context;
    private UnitsFormat unitsFormat;
    private Languages languages;

    public void initSettingsController(Context mainActivityContext){
        defaultSettings = new HashMap<>();
        context = mainActivityContext;
        unitsFormat = new UnitsFormat();
        languages = new Languages();
        prepareDefaultSettings();
    }

    public void saveSettings(Map<String, String> map){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SettingsWeather", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.apply();
    }

    public Map<String, String> getSettings(){
        Map<String, String> map = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("SettingsWeather", MODE_PRIVATE);
        for(Map.Entry<String, String> entry : defaultSettings.entrySet()) {
            if (sharedPreferences.contains(entry.getKey())) {
                if(!TextUtils.equals(entry.getValue(), unitsFormat.getIN_KELVIN().getValue())) {
                    map.put(entry.getKey(), sharedPreferences.getString(entry.getKey(), ""));
                }
            }else map.put(entry.getKey(), defaultSettings.get(entry.getKey()));
        }return map;
    }

    public void resetByDefault(){
        saveSettings(defaultSettings);
    }

    private void prepareDefaultSettings(){
        defaultSettings.put("units", unitsFormat.getIN_CELSIUS().getValue());
        defaultSettings.put("lang", languages.getEnglish().getValue());
        defaultSettings.put("appid", ApiKey.API_key);
    }
    public List<Lang> getLanguages(){
       return languages.getListLanguages();
    }
}
