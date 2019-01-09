package com.dogceo.breedvisualizer.model;

import com.google.gson.annotations.SerializedName;

public class BreedImageResponse extends BaseResponse {

    @SerializedName("message")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

}
