package com.example.cs411project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class googlePlaces extends AppCompatActivity {

    PlacesClient placesClient;
    List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS);

    private String placeID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPlaces();
    }

    private void initPlaces() {
        Places.initialize(this, getString(R.string.googlePlaces_API_KEY));
        placesClient = Places.createClient(this);
    }

    private void requestLocationPermission() {
        Dexter.withContext(this).withPermissions(Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        Toast.makeText(googlePlaces.this, "Please enable location sharing", Toast.LENGTH_SHORT).show();
                    }
                }).check();
    }

    private void getCurrLocation() {
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.builder(placeFields).build();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<FindCurrentPlaceResponse> responseTask = placesClient.findCurrentPlace(request);
        responseTask.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
            @Override
            public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                if(task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    Collections.sort(response.getPlaceLikelihoods(), new Comparator<PlaceLikelihood>() {
                        @Override
                        public int compare(PlaceLikelihood placeLikelihood, PlaceLikelihood t1) {
                            return new Double(placeLikelihood.getLikelihood()).compareTo(t1.getLikelihood());
                        }
                    });
                    Collections.reverse(response.getPlaceLikelihoods());
                    placeID = response.getPlaceLikelihoods().get(0).getPlace().getId();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(googlePlaces.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
