package com.dogceo.breedvisualizer;

import android.app.Application;

import com.dogceo.breedvisualizer.endpoint.DogCeoApiClient;
import com.dogceo.breedvisualizer.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static DogCeoApiClient dogCeoApi;

    @Override
    public void onCreate() {
        super.onCreate();

        configureEndpoint();
    }

    private void configureEndpoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Endpoint.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dogCeoApi = retrofit.create(DogCeoApiClient.class);
    }

}
