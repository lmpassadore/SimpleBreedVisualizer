package com.dogceo.breedvisualizer.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BreedListResponse extends BaseResponse {

    @SerializedName("message")
    private ArrayList<String> dogBreeds;

    public ArrayList<String> getDogBreeds() {
        return dogBreeds;
    }

}
