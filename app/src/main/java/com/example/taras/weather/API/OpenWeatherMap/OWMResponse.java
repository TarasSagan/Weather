
package com.example.taras.weather.API.OpenWeatherMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OWMResponse {

    @SerializedName("cnt")
    @Expose
    private long cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public long getCnt() {
        return cnt;
    }

    public void setCnt(long cnt) {
        this.cnt = cnt;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

}
