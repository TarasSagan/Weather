
package com.example.taras.weather.repository.modelsResponse.FindCities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindCitiesResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("count")
    @Expose
    private long count;
    @SerializedName("list")
    @Expose
    private java.util.List<com.example.taras.weather.repository.modelsResponse.FindCities.List> list = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public java.util.List<com.example.taras.weather.repository.modelsResponse.FindCities.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.taras.weather.repository.modelsResponse.FindCities.List> list) {
        this.list = list;
    }

}
