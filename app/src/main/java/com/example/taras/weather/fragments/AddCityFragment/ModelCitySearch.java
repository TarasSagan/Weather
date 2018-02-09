package com.example.taras.weather.fragments.AddCityFragment;



public class ModelCitySearch {
    private Double temp;
    private String description;
    private String cityCountry;
    private String cityName;
    private Long cityID;

    public ModelCitySearch(Double temp, String description, String cityCountry, String cityName, Long cityID) {
        this.temp = temp;
        this.description = description;
        this.cityCountry = cityCountry;
        this.cityName = cityName;
        this.cityID = cityID;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCityID() {
        return cityID;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }
}
