package com.example.taras.weather.repository.local.fiveDaysThreeHours;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
@Entity
public class Repo{
    @PrimaryKey
    private final long id;
    private final long lastUpdate;
    private final String cityName;
    private final long cityID;
    private final long forecastDate;
    private final double temperature;
    private final String description;
    private final long humidity;
    private final double pressure;
    private final double windSpeed;
    private final double windDirection;
    private final long clouds ;

    public Repo(long id, long lastUpdate, String cityName, long cityID, long forecastDate, double temperature, String description, long humidity, double pressure, double windSpeed, double windDirection, long clouds) {
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

    public long getId() {
        return id;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public String getCityName() {
        return cityName;
    }

    public long getCityID() {
        return cityID;
    }

    public long getForecastDate() {
        return forecastDate;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public long getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public long getClouds() {
        return clouds;
    }
}
