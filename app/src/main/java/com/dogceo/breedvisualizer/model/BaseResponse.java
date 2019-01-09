package com.dogceo.breedvisualizer.model;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

}
