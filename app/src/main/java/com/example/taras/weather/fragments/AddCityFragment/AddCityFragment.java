package com.example.taras.weather.fragments.AddCityFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taras.weather.R;
import com.example.taras.weather.fragments.AddCityFragment.ListCitiesFragment.ListCitiesFragment;
import com.example.taras.weather.repository.modelsResponse.FindCities.FindCitiesResponse;

import io.reactivex.Observable;


public class AddCityFragment extends DialogFragment  {
    final String TAG = "AddCityFragment";
    private ListCitiesFragment listCitiesFragment;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private EditText editText;
    TextView textView;



//        finCityOObservable.subscribe(text -> textView.setText(text));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_city_fragment, null);
        editText = view.findViewById(R.id.editText);
        listCitiesFragment = new ListCitiesFragment();
        textView = view.findViewById(R.id.textView);
        Observable<String> finCityOObservable = RxEditText.getTextWatherObservable(editText);
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.containerListCities, listCitiesFragment);
        transaction.commit();
        return view;
    }



}
