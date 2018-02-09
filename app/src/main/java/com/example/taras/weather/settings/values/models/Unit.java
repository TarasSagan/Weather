package com.example.taras.weather.settings.values.models;



public class Unit {
    public Unit(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    private String description;
    private String value;
}
