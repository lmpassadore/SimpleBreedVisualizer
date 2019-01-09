package com.dogceo.breedvisualizer.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.dogceo.breedvisualizer.App;
import com.dogceo.breedvisualizer.R;
import com.dogceo.breedvisualizer.fragment.BreedImageDialogFragment;
import com.dogceo.breedvisualizer.model.BreedImageResponse;
import com.dogceo.breedvisualizer.model.BreedListResponse;
import com.dogceo.breedvisualizer.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedListActivity extends AppCompatActivity {

    private ContentLoadingProgressBar progressBar;
    private ListView breedsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed_list);

        progressBar = findViewById(R.id.breedlist_progressbar_loading);
        breedsListView = findViewById(R.id.breedlist_listview_breeds);

        requestDogBreeds();
    }

    private void requestDogBreeds() {

        showProgressBar(true);

        App.dogCeoApi.getBreedList().enqueue(new Callback<BreedListResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedListResponse> call, @NonNull Response<BreedListResponse> response) {

                showProgressBar(false);
                BreedListResponse responseBody = response.body();

                if (!response.isSuccessful() || responseBody == null || !responseBody.getStatus().equals(Constants.Endpoint.REQUEST_SUCCESS)) {
                    Toast.makeText(BreedListActivity.this, getString(R.string.breedlist_network_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                initializeBreedsList(responseBody.getDogBreeds());
            }

            @Override
            public void onFailure(@NonNull Call<BreedListResponse> call, @NonNull Throwable t) {
                showProgressBar(false);
                Toast.makeText(BreedListActivity.this, getString(R.string.breedlist_network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeBreedsList(final ArrayList<String> breeds) {

        breedsListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, breeds));
        breedsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                requestBreedImage(breeds.get(position));
            }
        });

    }

    private void requestBreedImage(final String breedName) {
        App.dogCeoApi.getBreedImage(breedName).enqueue(new Callback<BreedImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<BreedImageResponse> call, @NonNull Response<BreedImageResponse> response) {

                showProgressBar(false);

                BreedImageResponse responseBody = response.body();
                if (!response.isSuccessful() || responseBody == null || !responseBody.getStatus().equals(Constants.Endpoint.REQUEST_SUCCESS)) {
                    Toast.makeText(BreedListActivity.this, getString(R.string.breedlist_network_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                showBreedDetails(breedName, responseBody.getImageUrl());

            }

            @Override
            public void onFailure(@NonNull Call<BreedImageResponse> call, @NonNull Throwable t) {
                showProgressBar(false);
                Toast.makeText(BreedListActivity.this, getString(R.string.breedlist_network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBreedDetails(String breedName, String imageUrl) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        BreedImageDialogFragment dialogFragment = BreedImageDialogFragment.newInstance(breedName, imageUrl);
        dialogFragment.show(fragmentManager, "dialog_breed_detail");
    }

    protected void showProgressBar(boolean show) {
        if (progressBar == null)
            return;

        if (show)
            progressBar.show();
        else
            progressBar.hide();
    }

}
