package com.example.taras.weather;

/**
 * Created by Taras on 31.10.2017.
 */

public class ItemForecast {
    private int id;
    private long lastUpdate;
    private String cityName;
    private long cityID;
    private long forecastDate;
    private double temperature;
    private String description;
    private long humidity;
    private double pressure;
    private double windSpeed;
    private double windDirection;
    private long clouds ;

    public ItemForecast(int id, long lastUpdate, String cityName, long cityID, long forecastDate,
                        double temperature, String description, long humidity, double pressure,
                        double windSpeed, double windDirection, long clouds) {
        this.id = id;
        this.lastUpdate = lastUpdate;
        this.cityName = cityName;
        this.cityID = cityID;
        this.forecastDate = forecastDate;
        this.temperature = temperature;
        this.description = description;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.clouds = clouds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCityID() {
        return cityID;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
    }

    public long getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(long forecastDate) {
        this.forecastDate = forecastDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public long getClouds() {
        return clouds;
    }

    public void setClouds(long clouds) {
        this.clouds = clouds;
    }
}
