package com.example.taras.weather.settings.values;


import com.example.taras.weather.settings.values.models.Unit;

import java.util.ArrayList;
import java.util.List;

public class UnitsFormat {
    private Unit IN_FAHRENHEIT = new Unit("°F", "imperial");
    private Unit IN_CELSIUS = new Unit("°C","metric");
    private Unit IN_KELVIN = new Unit("К", "Kelvin");




    public Unit getIN_FAHRENHEIT() {
        return IN_FAHRENHEIT;
    }

    public Unit getIN_CELSIUS() {
        return IN_CELSIUS;
    }

    public Unit getIN_KELVIN() {
        return IN_KELVIN;
    }
    public List<Unit> getListUnits(){
        List<Unit> list = new ArrayList<>();
        list.add(IN_FAHRENHEIT);
        list.add(IN_CELSIUS);
        list.add(IN_KELVIN);
        return list;
    }
}
