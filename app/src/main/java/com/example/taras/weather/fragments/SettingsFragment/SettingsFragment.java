package com.example.taras.weather.fragments.SettingsFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.taras.weather.MainActivity;
import com.example.taras.weather.R;
import com.example.taras.weather.settings.values.UnitsFormat;
import com.example.taras.weather.settings.values.models.Lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsFragment extends Fragment implements RecyclerAdapterCallback{
    public static final String TAG = "SettingsFragment_TAG";
    private RecyclerView recyclerView;
    private RadioButton radioButtonCelsius, radioButtonFahrenheit, radioButtonKelvin;
    private RecyclerViewAdapter recyclerViewAdapter;
    private Button buttonShowList;
    private TextView textViewSelected;
    private List<Lang> langList;
    private Map<String, String> settings;
    private Map<String, String> newSettings;
    private UnitsFormat unitsFormat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        settings = MainActivity.getSettingsController().getSettings();
        newSettings = new HashMap<>();
        unitsFormat = new UnitsFormat();
        langList = MainActivity.getSettingsController().getLanguages();
        findViewById(view);
        setOnClickListeners();
        setContentInView();

        return view;
    }
    void onClickShowButton(){
        if(recyclerViewAdapter != null){
            langList.clear();
            recyclerViewAdapter.notifyDataSetChanged();
            recyclerViewAdapter = null;
            langList = MainActivity.getSettingsController().getLanguages();
        }else initRecyclerView();
    }
    void initRecyclerView(){
        if(langList.isEmpty()){
            langList = MainActivity.getSettingsController().getLanguages();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(langList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
    void findViewById(View view){
        radioButtonCelsius = (RadioButton) view.findViewById(R.id.radioButtonCelsius);
        radioButtonFahrenheit = (RadioButton) view.findViewById(R.id.radioButtonFahrenheit);
        radioButtonKelvin = (RadioButton) view.findViewById(R.id.radioButtonKelvin);
        buttonShowList = (Button) view.findViewById(R.id.buttonShowList);
        textViewSelected = (TextView) view.findViewById(R.id.textViewSelected);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSettings);
    }
    private void setContentInView(){
        textViewSelected.setText(findLangDescription(settings.get("lang")));
        if(TextUtils.equals(settings.get("units"), unitsFormat.getIN_CELSIUS().getValue())){
            radioButtonCelsius.setChecked(true);
        }else if (TextUtils.equals(settings.get("units"), unitsFormat.getIN_FAHRENHEIT().getValue())){
            radioButtonFahrenheit.setChecked(true);
        }else if (TextUtils.equals(settings.get("units"), unitsFormat.getIN_KELVIN().getValue())){
            radioButtonKelvin.setChecked(true);
        }
    }
    private void setOnClickListeners(){
        textViewSelected.setOnClickListener(v -> onClickShowButton());
        buttonShowList.setOnClickListener(v -> onClickShowButton());
        radioButtonCelsius.setOnClickListener(v  -> {
            newSettings.put("units", unitsFormat.getIN_CELSIUS().getValue());
            saveSettings();
        });
        radioButtonFahrenheit.setOnClickListener(v -> {
            newSettings.put("units", unitsFormat.getIN_FAHRENHEIT().getValue());
            saveSettings();
        });
        radioButtonKelvin.setOnClickListener(v -> {
            newSettings.put("units", unitsFormat.getIN_KELVIN().getValue());
            saveSettings();
        });
    }
    private void saveSettings(){
        MainActivity.getSettingsController().saveSettings(newSettings);
        settings.clear();
        settings = MainActivity.getSettingsController().getSettings();
        setContentInView();
    }
    private String findLangDescription(String val){
        for(Lang lang : langList){
            if (TextUtils.equals(val, lang.getValue())){
                return lang.getDescription();
            }
        }return "Not found";
    }


    @Override
    public void selectLang(Lang lang) {
        newSettings.put("lang", lang.getValue());
        saveSettings();
        onClickShowButton();
    }

}
