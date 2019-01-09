package com.dogceo.breedvisualizer.endpoint;

import com.dogceo.breedvisualizer.model.BreedImageResponse;
import com.dogceo.breedvisualizer.model.BreedListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogCeoApiClient {

    @GET("api/breeds/list")
    Call<BreedListResponse> getBreedList();

    @GET("api/breed/{breed}/images/random")
    Call<BreedImageResponse> getBreedImage(
        @Path("breed") String breedName
    );

}
