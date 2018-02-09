package com.example.taras.weather.settings.values.models;


public  class Lang {
    public Lang(String description, String value) {
        this.description = description;
        this.value = value;
    }

    private String description;
    private String value;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
