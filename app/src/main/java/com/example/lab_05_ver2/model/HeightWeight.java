package com.example.lab_05_ver2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HeightWeight implements Serializable {
    @SerializedName("imperial")
    private String imperial;

    @SerializedName("metric")
    private String metric;

    public HeightWeight(String imperial, String metric) {
        this.imperial = imperial;
        this.metric = metric;
    }

    public String getImperial() {
        return imperial;
    }

    public void setImperial(String imperial) {
        this.imperial = imperial;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
