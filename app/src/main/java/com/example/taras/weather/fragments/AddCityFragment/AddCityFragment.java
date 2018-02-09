package com.example.taras.weather.fragments.AddCityFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taras.weather.MainActivity;
import com.example.taras.weather.R;
import com.example.taras.weather.repository.api.ClientRetrofit;
import com.example.taras.weather.repository.api.ForecastUpdate;
import com.example.taras.weather.repository.api.OpenWeatherMapAPI;
import com.example.taras.weather.repository.modelsResponse.FindCities.SearchCitiesResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AddCityFragment extends Fragment {
    private CityRecyclerViewAdapter cityRecyclerViewAdapter;
    private OpenWeatherMapAPI openWeatherMapAPI = new ClientRetrofit().getClient();
    private Map<String, String> tmpMap = new HashMap<>();
    private List<ModelCitySearch> listCities = new ArrayList<>();
    public static final String TAG = "AddCityFragment";
    private EditText editText;
    private Button button;
    private Map<String, String> settingsMap;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_city_fragment, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listFoundCities);
        Context context = view.getContext();
        settingsMap = MainActivity.getSettingsController().getSettings();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        cityRecyclerViewAdapter = new CityRecyclerViewAdapter(listCities, (MainActivity)getActivity());
        recyclerView.setAdapter(cityRecyclerViewAdapter);
        button = (Button) view.findViewById(R.id.btnSearch);
        editText = (EditText) view.findViewById(R.id.editText);
        button.setOnClickListener(v -> citySearch(editText.getText().toString()));
        return view;
    }
    private void citySearch(String editable){
        if (isNetworkConnected()) {
            if (!TextUtils.isEmpty(editText.getText().toString())) {
                button.setEnabled(false);
                tmpMap.put("appid", settingsMap.get("appid"));
                tmpMap.put("lang", settingsMap.get("lang"));
                tmpMap.put("units", settingsMap.get("units"));
                tmpMap.put("q", editable);
                tmpMap.put("type", "like");
                openWeatherMapAPI.findCity(tmpMap)
                        .map(response -> {
                            listCities.clear();
                            for (int i = 0; i < response.getList().size(); i++) {
                                listCities.add(new ModelCitySearch(
                                        response.getList().get(i).getMain().getTemp(),
                                        response.getList().get(i).getWeather().get(0).getDescription(),
                                        response.getList().get(i).getSys().getCountry(),
                                        response.getList().get(i).getName(),
                                        response.getList().get(i).getId()
                                ));
                            }
                            return listCities;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable -> showSnackBar(throwable.getLocalizedMessage()))
                        .subscribe(citiesResponseList -> {
                            button.setEnabled(true);
                            cityRecyclerViewAdapter.notifyDataSetChanged();
                        });
            }
        }else showSnackBar("No internet connection");
    }
    private void showSnackBar(String info){
        Snackbar.make(view, info, Snackbar.LENGTH_LONG).show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }
    public interface AddCityListListener {
        void addCityCallback(ModelCitySearch searchCitiesResponseItem);
    }

}
