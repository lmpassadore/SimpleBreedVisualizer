package com.dogceo.breedvisualizer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import com.dogceo.breedvisualizer.R;

public class BreedImageDialogFragment extends DialogFragment {

    static final String EXTRA_NAME = "EXTRA_NAME";
    static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    public BreedImageDialogFragment() {

    }

    public static BreedImageDialogFragment newInstance(String breedName, String imageUrl) {
        BreedImageDialogFragment dialogFragment = new BreedImageDialogFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, breedName);
        args.putString(EXTRA_IMAGE_URL, imageUrl);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breed_image, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ContentLoadingProgressBar progressBar = view.findViewById(R.id.breedimage_progressbar_loading);
        progressBar.show();

        TextView textViewName = view.findViewById(R.id.breedimage_textview_breedname);
        ImageView imageViewPhoto = view.findViewById(R.id.breedimage_imageview_photo);

        Bundle args = getArguments();
        if (args == null)
            return;

        String breedName = args.getString(EXTRA_NAME);
        textViewName.setText(breedName);

        String imageUrl = args.getString(EXTRA_IMAGE_URL);
        Picasso.get()
                .load(imageUrl)
                .resize(200, 140)
                .centerInside()
                .into(imageViewPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.hide();
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.hide();
                    }
                });

    }

}
